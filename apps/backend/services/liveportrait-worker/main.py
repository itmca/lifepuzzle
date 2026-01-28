#!/usr/bin/env python3
"""
LivePortrait Worker - AI Video Generation Service

This worker consumes events from RabbitMQ and generates AI videos using LivePortrait.
It runs natively on macOS to utilize MPS (Metal Performance Shaders) acceleration.
"""

import logging
import sys

from config import Config
from rabbitmq import RabbitMQConsumer
from services import VideoGenerator

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s",
    handlers=[
        logging.StreamHandler(sys.stdout),
    ],
)

logger = logging.getLogger(__name__)


def main():
    logger.info("=" * 60)
    logger.info("LivePortrait Worker Starting")
    logger.info("=" * 60)
    logger.info(f"RabbitMQ: {Config.RABBITMQ_HOST}:{Config.RABBITMQ_PORT}")
    logger.info(f"Queue: {Config.QUEUE_NAME}")
    logger.info(f"Database: {Config.DB_HOST}:{Config.DB_PORT}/{Config.DB_NAME}")
    logger.info(f"LivePortrait Path: {Config.LIVEPORTRAIT_PATH}")
    logger.info(f"Conda Environment: {Config.CONDA_ENV}")
    logger.info("=" * 60)

    # Initialize video generator
    video_generator = VideoGenerator()

    def handle_message(data: dict) -> bool:
        """Handle incoming video generation event."""
        hero_id = data.get("heroId")
        gallery_id = data.get("galleryId")
        driving_video_id = data.get("drivingVideoId")

        if not all([hero_id, gallery_id, driving_video_id]):
            logger.error(f"Invalid event data: {data}")
            return False

        logger.info(
            f"Processing video generation: hero_id={hero_id}, "
            f"gallery_id={gallery_id}, driving_video_id={driving_video_id}"
        )

        return video_generator.process_event(hero_id, gallery_id, driving_video_id)

    # Start consumer
    consumer = RabbitMQConsumer(handle_message)

    try:
        consumer.run_with_reconnect()
    except KeyboardInterrupt:
        logger.info("Shutdown requested by user")
    finally:
        consumer.close()

    logger.info("LivePortrait Worker stopped")


if __name__ == "__main__":
    main()
