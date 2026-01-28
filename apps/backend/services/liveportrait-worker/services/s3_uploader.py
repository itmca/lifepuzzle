import logging
import os
from datetime import datetime
from pathlib import Path

import boto3
from botocore.exceptions import ClientError

from config import Config

logger = logging.getLogger(__name__)


class S3Uploader:
    def __init__(self):
        self.s3_client = boto3.client(
            "s3",
            aws_access_key_id=Config.AWS_ACCESS_KEY,
            aws_secret_access_key=Config.AWS_SECRET_KEY,
            region_name=Config.AWS_REGION,
        )
        self.bucket = Config.AWS_S3_BUCKET

    def upload_video(self, file_path: str, hero_no: int, gallery_id: int, driving_video_id: int) -> str:
        """Upload video file to S3 and return the S3 URL."""
        try:
            file_path = Path(file_path)
            if not file_path.exists():
                raise FileNotFoundError(f"Video file not found: {file_path}")

            # Generate S3 key
            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
            s3_key = f"ai-videos/hero_{hero_no}/gallery_{gallery_id}_driving_{driving_video_id}_{timestamp}.mp4"

            logger.info(f"Uploading video to S3: {s3_key}")

            # Upload file
            self.s3_client.upload_file(
                str(file_path),
                self.bucket,
                s3_key,
                ExtraArgs={"ContentType": "video/mp4"},
            )

            # Generate URL
            s3_url = f"https://{self.bucket}.s3.{Config.AWS_REGION}.amazonaws.com/{s3_key}"

            logger.info(f"Video uploaded successfully: {s3_url}")
            return s3_url

        except ClientError as e:
            logger.error(f"Failed to upload video to S3: {e}")
            raise
        except Exception as e:
            logger.error(f"Unexpected error during S3 upload: {e}")
            raise
