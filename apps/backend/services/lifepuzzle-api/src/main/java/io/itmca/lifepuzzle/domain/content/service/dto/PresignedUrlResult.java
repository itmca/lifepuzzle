package io.itmca.lifepuzzle.domain.content.service.dto;

import java.util.List;

public record PresignedUrlResult(List<PresignedUrlItem> urls) {

  public record PresignedUrlItem(
      String fileKey,
      String presignedUrl,
      Headers headers
  ) {
    public record Headers(String host, String contentType, String cacheControl) {}
  }
}
