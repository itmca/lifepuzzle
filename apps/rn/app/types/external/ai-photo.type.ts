export type AiPhotoTemplate = {
  id: number;
  name: string;
  url: string;
  thumbnailUrl: string;
  description?: string;
};

export type AiType = 'AI_PHOTO';

export type VideoGenerationStatus =
  | 'PENDING'
  | 'IN_PROGRESS'
  | 'COMPLETED'
  | 'FAILED';

// Backend: AiGeneratedVideoDto
export type AiGeneratedVideo = {
  id: number;
  galleryId: number;
  drivingVideoId: number;
  videoUrl?: string;
  status: VideoGenerationStatus;
  startedAt?: string;
  completedAt?: string;
  createdAt: string;
};
