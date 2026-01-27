package io.itmca.lifepuzzle.domain.content.endpoint;

import io.itmca.lifepuzzle.domain.content.endpoint.request.GalleryUpdateRequest;
import io.itmca.lifepuzzle.domain.content.endpoint.request.GalleryUploadCompleteRequest;
import io.itmca.lifepuzzle.domain.content.endpoint.request.PresignedUrlRequest;
import io.itmca.lifepuzzle.domain.content.endpoint.response.GalleryUploadCompleteResponse;
import io.itmca.lifepuzzle.domain.content.endpoint.response.PresignedUrlResponse;
import io.itmca.lifepuzzle.domain.content.service.GalleryWriteService;
import io.itmca.lifepuzzle.domain.content.service.GalleryWriteService.FileInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Gallery Write", description = "갤러리 생성/수정/삭제")
public class GalleryWriteEndpoint {
  private final GalleryWriteService galleryWriteService;

  @PostMapping("/v1/galleries/presigned-urls")
  @Operation(summary = "Presigned URL 생성", description = "S3 파일 업로드를 위한 presigned URL을 생성합니다")
  public PresignedUrlResponse generatePresignedUrl(@Valid @RequestBody PresignedUrlRequest request) {
    var files = request.files().stream()
        .map(f -> new FileInfo(f.fileName(), f.contentType()))
        .toList();
    var result = galleryWriteService.createPresignedUrls(request.heroId(), request.ageGroup(), files);
    return PresignedUrlResponse.from(result);
  }

  @PostMapping("/v1/galleries/upload-complete")
  @Operation(summary = "업로드 완료 처리", description = "S3 업로드 완료 후 갤러리 상태를 업데이트합니다")
  public GalleryUploadCompleteResponse completeUpload(@Valid @RequestBody GalleryUploadCompleteRequest request) {
    var result = galleryWriteService.completeUploads(request.fileKeys());
    return GalleryUploadCompleteResponse.from(result);
  }

  @PatchMapping("/v1/galleries/{galleryId}")
  @Operation(summary = "갤러리 메타데이터 업데이트", description = "갤러리의 날짜와 나이 그룹을 업데이트합니다")
  public ResponseEntity<Void> updateGalleryMetadata(
      @PathVariable Long galleryId,
      @Valid @RequestBody GalleryUpdateRequest request) {
    galleryWriteService.updateGalleryMetadata(galleryId, request.ageGroup(), request.date());
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/v1/galleries/{galleryId}")
  @Operation(summary = "갤러리 삭제", description = "갤러리 항목을 삭제합니다")
  public ResponseEntity<Void> deleteGalleryItem(@PathVariable Long galleryId) {
    galleryWriteService.deleteGalleryItem(galleryId);
    return ResponseEntity.ok().build();
  }
}
