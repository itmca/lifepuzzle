from datetime import datetime
from enum import Enum
from typing import Optional

from sqlalchemy import create_engine, Column, BigInteger, String, DateTime, Text, Integer, Enum as SQLEnum
from sqlalchemy.orm import sessionmaker, declarative_base

from config import Config

Base = declarative_base()

engine = create_engine(Config.get_database_url(), echo=False)
SessionLocal = sessionmaker(bind=engine)


def get_session():
    return SessionLocal()


class VideoGenerationStatus(str, Enum):
    PENDING = "PENDING"
    IN_PROGRESS = "IN_PROGRESS"
    COMPLETED = "COMPLETED"
    FAILED = "FAILED"


class AiGeneratedVideo(Base):
    __tablename__ = "ai_generated_video"

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    hero_no = Column("hero_no", BigInteger, nullable=False)
    gallery_id = Column("gallery_id", BigInteger, nullable=False)
    driving_video_id = Column("driving_video_id", BigInteger, nullable=False)
    video_url = Column("video_url", String(500), nullable=True)
    status = Column(String(20), nullable=False, default=VideoGenerationStatus.PENDING.value)
    started_at = Column("started_at", DateTime, nullable=True)
    completed_at = Column("completed_at", DateTime, nullable=True)
    error_message = Column("error_message", Text, nullable=True)
    deleted_at = Column("deleted_at", DateTime, nullable=True)
    created_at = Column("created_at", DateTime, nullable=False, default=datetime.now)
    updated_at = Column("updated_at", DateTime, nullable=False, default=datetime.now, onupdate=datetime.now)

    def mark_as_started(self):
        self.status = VideoGenerationStatus.IN_PROGRESS.value
        self.started_at = datetime.now()
        self.updated_at = datetime.now()

    def mark_as_completed(self, video_url: str):
        self.status = VideoGenerationStatus.COMPLETED.value
        self.video_url = video_url
        self.completed_at = datetime.now()
        self.updated_at = datetime.now()

    def mark_as_failed(self, error_message: str):
        self.status = VideoGenerationStatus.FAILED.value
        self.error_message = error_message
        self.completed_at = datetime.now()
        self.updated_at = datetime.now()


class Gallery(Base):
    __tablename__ = "gallery"

    id = Column(BigInteger, primary_key=True)
    hero_id = Column("hero_id", BigInteger, nullable=False)
    url = Column(String(500), nullable=False)
    created_at = Column("created_at", DateTime, nullable=False)
    updated_at = Column("updated_at", DateTime, nullable=False)


class AiDrivingVideo(Base):
    __tablename__ = "ai_driving_video"

    id = Column(BigInteger, primary_key=True)
    name = Column(String(100), nullable=False)
    url = Column(String(500), nullable=False)
    thumbnail_url = Column("thumbnail_url", String(500), nullable=True)
    description = Column(Text, nullable=True)
    priority = Column(Integer, nullable=False, default=0)
    deleted_at = Column("deleted_at", DateTime, nullable=True)
    created_at = Column("created_at", DateTime, nullable=False)
    updated_at = Column("updated_at", DateTime, nullable=False)
