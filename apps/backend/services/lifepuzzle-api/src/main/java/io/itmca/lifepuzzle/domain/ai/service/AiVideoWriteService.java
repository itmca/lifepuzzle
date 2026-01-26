package io.itmca.lifepuzzle.domain.ai.service;

import io.itmca.lifepuzzle.domain.ai.event.AiVideoCreateEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AiVideoWriteService {

  private final AiVideoCreateEventPublisher aiVideoCreateEventPublisher;

  public void generateAiVideo(Long heroId, Long galleryId, Long drivingVideoId) {
    aiVideoCreateEventPublisher.publishPhotoUploadEvent(heroId, galleryId, drivingVideoId);
  }
}
