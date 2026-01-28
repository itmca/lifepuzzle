import os
from dotenv import load_dotenv

load_dotenv()


class Config:
    # RabbitMQ
    RABBITMQ_HOST = os.getenv("RABBITMQ_HOST", "localhost")
    RABBITMQ_PORT = int(os.getenv("RABBITMQ_PORT", "5672"))
    RABBITMQ_USERNAME = os.getenv("RABBITMQ_USERNAME", "guest")
    RABBITMQ_PASSWORD = os.getenv("RABBITMQ_PASSWORD", "guest")
    RABBITMQ_VHOST = os.getenv("RABBITMQ_VHOST", "/")

    # Queue settings
    QUEUE_NAME = os.getenv("QUEUE_NAME", "gallery.ai.video.create.ai-video-generator")

    # Database
    DB_HOST = os.getenv("DB_HOST", "localhost")
    DB_PORT = int(os.getenv("DB_PORT", "3306"))
    DB_NAME = os.getenv("DB_NAME", "lifepuzzle")
    DB_USER = os.getenv("DB_USER", "root")
    DB_PASSWORD = os.getenv("DB_PASSWORD", "password")

    @classmethod
    def get_database_url(cls) -> str:
        return f"mysql+pymysql://{cls.DB_USER}:{cls.DB_PASSWORD}@{cls.DB_HOST}:{cls.DB_PORT}/{cls.DB_NAME}"

    # AWS S3
    AWS_ACCESS_KEY = os.getenv("AWS_ACCESS_KEY", "")
    AWS_SECRET_KEY = os.getenv("AWS_SECRET_KEY", "")
    AWS_S3_BUCKET = os.getenv("AWS_S3_BUCKET", "")
    AWS_REGION = os.getenv("AWS_REGION", "ap-northeast-2")

    # LivePortrait
    LIVEPORTRAIT_PATH = os.getenv("LIVEPORTRAIT_PATH", "/Users/jeong/lifepuzzle/external/LivePortrait")
    CONDA_ENV = os.getenv("CONDA_ENV", "LivePortrait")

    # Paths
    DOWNLOAD_PATH = os.getenv("DOWNLOAD_PATH", "/var/tmp/ai_video_download")
    OUTPUT_PATH = os.getenv("OUTPUT_PATH", "/var/tmp/ai_video")
