package io.itmca.lifepuzzle.domain.ai.service;

import io.itmca.lifepuzzle.domain.ai.entity.AiGeneratedVideo;
import io.itmca.lifepuzzle.domain.ai.event.AiVideoCreateEventPublisher;
import io.itmca.lifepuzzle.domain.ai.repository.AiGeneratedVideoRepository;
import io.itmca.lifepuzzle.domain.ai.type.VideoGenerationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AiVideoWriteService {

  private final AiGeneratedVideoRepository aiGeneratedVideoRepository;
  private final AiVideoCreateEventPublisher aiVideoCreateEventPublisher;

  /**
   * AI 비디오 생성 요청 처리
   *
   * 1. 중복 요청 체크 (이미 진행 중인 요청이 있는지)
   * 2. PENDING 상태의 레코드 생성
   * 3. RabbitMQ로 이벤트 발행
   *
   * @return 생성된 AiGeneratedVideo
   * @throws IllegalStateException 이미 진행 중인 요청이 있는 경우
   */
  public AiGeneratedVideo generateAiVideo(Long heroId, Long galleryId, Long drivingVideoId) {
    // 중복 요청 체크: 동일 조합으로 PENDING/IN_PROGRESS 상태가 있으면 예외
    var existingVideo = aiGeneratedVideoRepository
        .findByHeroNoAndGalleryIdAndDrivingVideoIdAndStatusIn(
            heroId, galleryId, drivingVideoId,
            VideoGenerationStatus.PENDING, VideoGenerationStatus.IN_PROGRESS
        );

    if (existingVideo.isPresent()) {
      log.info("Already processing video generation for heroId: {}, galleryId: {}, drivingVideoId: {}",
          heroId, galleryId, drivingVideoId);
      return existingVideo.get();
    }

    // PENDING 상태의 새 레코드 생성
    var video = AiGeneratedVideo.builder()
        .heroNo(heroId)
        .galleryId(galleryId)
        .drivingVideoId(drivingVideoId)
        .build();

    video = aiGeneratedVideoRepository.save(video);
    log.info("Created AI video generation request with id: {}", video.getId());

    // 이벤트 발행 (ai-video-generator가 consume)
    aiVideoCreateEventPublisher.publishPhotoUploadEvent(heroId, galleryId, drivingVideoId);

    return video;
  }
}
