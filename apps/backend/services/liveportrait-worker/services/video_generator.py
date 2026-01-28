import logging
import os
import subprocess
from pathlib import Path
from typing import Optional
from urllib.parse import urlparse

import requests

from config import Config
from db import get_session, AiGeneratedVideo, Gallery, AiDrivingVideo
from db.models import VideoGenerationStatus
from .s3_uploader import S3Uploader

logger = logging.getLogger(__name__)


class VideoGenerator:
    def __init__(self):
        self.s3_uploader = S3Uploader()
        self.download_path = Path(Config.DOWNLOAD_PATH)
        self.output_path = Path(Config.OUTPUT_PATH)

        # Ensure directories exist
        self.download_path.mkdir(parents=True, exist_ok=True)
        self.output_path.mkdir(parents=True, exist_ok=True)

    def process_event(self, hero_id: int, gallery_id: int, driving_video_id: int) -> bool:
        """Process a video generation event."""
        session = get_session()

        try:
            # Find PENDING video record
            video = (
                session.query(AiGeneratedVideo)
                .filter(
                    AiGeneratedVideo.hero_no == hero_id,
                    AiGeneratedVideo.gallery_id == gallery_id,
                    AiGeneratedVideo.driving_video_id == driving_video_id,
                    AiGeneratedVideo.status == VideoGenerationStatus.PENDING.value,
                )
                .first()
            )

            if not video:
                logger.warning(
                    f"No PENDING video record found for hero_id={hero_id}, "
                    f"gallery_id={gallery_id}, driving_video_id={driving_video_id}"
                )
                return False

            logger.info(f"Found PENDING video record: id={video.id}")

            # Mark as started
            video.mark_as_started()
            session.commit()

            # Get gallery and driving video info
            gallery = session.query(Gallery).filter(Gallery.id == gallery_id).first()
            if not gallery:
                raise ValueError(f"Gallery not found: {gallery_id}")

            driving_video = (
                session.query(AiDrivingVideo)
                .filter(AiDrivingVideo.id == driving_video_id, AiDrivingVideo.deleted_at.is_(None))
                .first()
            )
            if not driving_video:
                raise ValueError(f"Driving video not found: {driving_video_id}")

            # Build S3 URLs and download files
            gallery_url = self._build_s3_url(gallery.url)
            driving_url = self._build_s3_url(driving_video.url)

            gallery_file = self._download_file(gallery_url, f"gallery_{gallery_id}")
            driving_file = self._download_file(driving_url, f"driving_{driving_video_id}")

            # Run LivePortrait
            output_file = self._run_liveportrait(gallery_file, driving_file, video.id)

            # Upload to S3
            s3_url = self.s3_uploader.upload_video(
                output_file, video.hero_no, gallery_id, driving_video_id
            )

            # Mark as completed
            video.mark_as_completed(s3_url)
            session.commit()

            # Cleanup local files
            self._cleanup_output_file(output_file)

            logger.info(f"Video generation completed successfully: id={video.id}, url={s3_url}")
            return True

        except Exception as e:
            logger.error(f"Video generation failed: {e}", exc_info=True)
            session.rollback()

            # Mark as failed
            try:
                if video:
                    video.mark_as_failed(str(e))
                    session.commit()
            except Exception as commit_error:
                logger.error(f"Failed to update video status: {commit_error}")

            return False

        finally:
            session.close()

    def _build_s3_url(self, path: str) -> str:
        """Build full S3 URL from path."""
        if path.startswith("http://") or path.startswith("https://"):
            return path

        clean_path = path.lstrip("/")
        return f"https://{Config.AWS_S3_BUCKET}.s3.{Config.AWS_REGION}.amazonaws.com/{clean_path}"

    def _download_file(self, url: str, filename: str) -> str:
        """Download file from URL and return local path."""
        # Get extension from URL
        parsed = urlparse(url)
        path = parsed.path
        ext = Path(path).suffix or ".jpg"

        local_path = self.download_path / f"{filename}{ext}"

        # Skip if already exists
        if local_path.exists():
            logger.info(f"File already exists, skipping download: {local_path}")
            return str(local_path)

        logger.info(f"Downloading: {url} -> {local_path}")

        response = requests.get(url, stream=True, timeout=60)
        response.raise_for_status()

        with open(local_path, "wb") as f:
            for chunk in response.iter_content(chunk_size=8192):
                f.write(chunk)

        logger.info(f"Download completed: {local_path}")
        return str(local_path)

    def _run_liveportrait(self, source_image: str, driving_video: str, video_id: int) -> str:
        """Run LivePortrait inference and return output file path."""
        logger.info(f"Running LivePortrait for video_id={video_id}")
        logger.info(f"Source image: {source_image}")
        logger.info(f"Driving video: {driving_video}")

        # Build command
        # On macOS with conda, we need to source conda first
        conda_init = "source $(conda info --base)/etc/profile.d/conda.sh"
        cmd = (
            f"{conda_init} && "
            f"conda activate {Config.CONDA_ENV} && "
            f"cd {Config.LIVEPORTRAIT_PATH} && "
            f"PYTORCH_ENABLE_MPS_FALLBACK=1 python inference.py "
            f"-s {source_image} "
            f"-d {driving_video} "
            f"-o {self.output_path}"
        )

        logger.info(f"Executing command: {cmd}")

        try:
            result = subprocess.run(
                ["bash", "-c", cmd],
                capture_output=True,
                text=True,
                timeout=1800,  # 30 minutes
                cwd=Config.LIVEPORTRAIT_PATH,
            )

            # Log output
            if result.stdout:
                for line in result.stdout.split("\n"):
                    if line.strip():
                        logger.info(f"LivePortrait: {line}")

            if result.returncode != 0:
                error_msg = result.stderr or "Unknown error"
                logger.error(f"LivePortrait failed: {error_msg}")
                raise RuntimeError(f"LivePortrait failed with exit code {result.returncode}: {error_msg}")

        except subprocess.TimeoutExpired:
            raise RuntimeError("LivePortrait timed out after 30 minutes")

        # Find output file (most recently modified mp4)
        output_file = self._find_latest_output()
        if not output_file:
            raise RuntimeError(f"No output video found in {self.output_path}")

        logger.info(f"LivePortrait output: {output_file}")
        return output_file

    def _find_latest_output(self) -> Optional[str]:
        """Find the most recently modified mp4 file in output directory."""
        mp4_files = list(self.output_path.glob("*.mp4"))

        # Exclude concat files
        mp4_files = [f for f in mp4_files if "_concat" not in f.name]

        if not mp4_files:
            return None

        # Return most recently modified
        latest = max(mp4_files, key=lambda f: f.stat().st_mtime)
        return str(latest)

    def _cleanup_output_file(self, file_path: str):
        """Delete local output file after upload."""
        try:
            Path(file_path).unlink()
            logger.info(f"Deleted local file: {file_path}")
        except Exception as e:
            logger.warning(f"Failed to delete local file {file_path}: {e}")
