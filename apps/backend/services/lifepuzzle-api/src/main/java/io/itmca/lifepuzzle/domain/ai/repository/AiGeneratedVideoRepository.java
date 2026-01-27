package io.itmca.lifepuzzle.domain.ai.repository;

import io.itmca.lifepuzzle.domain.ai.entity.AiGeneratedVideo;
import io.itmca.lifepuzzle.domain.ai.type.VideoGenerationStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AiGeneratedVideoRepository extends JpaRepository<AiGeneratedVideo, Long> {

  @Query("SELECT a FROM AiGeneratedVideo a WHERE a.heroNo = :heroNo AND a.deletedAt IS NULL ORDER BY a.createdAt DESC")
  List<AiGeneratedVideo> findByHeroNoAndNotDeleted(@Param("heroNo") Long heroNo);

  @Query("SELECT a FROM AiGeneratedVideo a WHERE a.deletedAt IS NULL ORDER BY a.createdAt DESC")
  List<AiGeneratedVideo> findAllActiveOrderByCreatedAtDesc();

  @Query("""
      SELECT a FROM AiGeneratedVideo a
      WHERE a.heroNo = :heroNo
        AND a.galleryId = :galleryId
        AND a.drivingVideoId = :drivingVideoId
        AND a.status IN :statuses
        AND a.deletedAt IS NULL
      ORDER BY a.createdAt DESC
      LIMIT 1
      """)
  Optional<AiGeneratedVideo> findByHeroNoAndGalleryIdAndDrivingVideoIdAndStatusIn(
      @Param("heroNo") Long heroNo,
      @Param("galleryId") Long galleryId,
      @Param("drivingVideoId") Long drivingVideoId,
      @Param("statuses") VideoGenerationStatus... statuses
  );
}