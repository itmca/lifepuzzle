package io.itmca.lifepuzzle.domain.content.endpoint.response;

import io.itmca.lifepuzzle.domain.content.service.dto.PresignedUrlResult;
import java.util.List;

public record PresignedUrlResponse(List<PresignedUrlDto> presignedUrls) {

  public static PresignedUrlResponse from(PresignedUrlResult result) {
    var dtos = result.urls().stream()
        .map(item -> new PresignedUrlDto(
            item.fileKey(),
            item.presignedUrl(),
            new PresignedUrlDto.Headers(
                item.headers().host(),
                item.headers().contentType(),
                item.headers().cacheControl()
            )
        ))
        .toList();
    return new PresignedUrlResponse(dtos);
  }

  public record PresignedUrlDto(String fileKey, String url, Headers headers) {

    public record Headers(String host, String contentType, String cacheControl) {}
  }
}