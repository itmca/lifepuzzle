package io.itmca.lifepuzzle.domain.content.endpoint.response;

import io.itmca.lifepuzzle.domain.content.endpoint.response.dto.GalleryUploadResultDto;
import io.itmca.lifepuzzle.domain.content.service.dto.UploadCompletionResult;
import java.util.List;

public record GalleryUploadCompleteResponse(
    List<GalleryUploadResultDto> results,
    int successCount,
    int failureCount
) {

  public static GalleryUploadCompleteResponse from(UploadCompletionResult result) {
    var dtos = result.results().stream()
        .map(r -> new GalleryUploadResultDto(r.fileKey(), r.galleryId(), r.status(), r.message()))
        .toList();
    return new GalleryUploadCompleteResponse(dtos, result.successCount(), result.failureCount());
  }
}