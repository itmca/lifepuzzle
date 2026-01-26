package io.itmca.lifepuzzle.domain.content.service.dto;

import io.itmca.lifepuzzle.domain.content.type.GalleryStatus;
import java.util.List;

public record UploadCompletionResult(
    List<GalleryResult> results,
    int successCount,
    int failureCount
) {

  public record GalleryResult(
      String fileKey,
      Long galleryId,
      GalleryStatus status,
      String message
  ) {}
}
