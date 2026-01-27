package io.itmca.lifepuzzle.domain.content.endpoint.response.dto;

import io.itmca.lifepuzzle.domain.content.entity.Story;
import io.itmca.lifepuzzle.domain.hero.entity.Hero;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record StoryDto(
    String id,
    Long heroNo,
    String content,
    List<String> photos,
    List<String> videos,
    String audioUrl,
    Integer audioDurationSeconds,
    List<LikeDto> likes,
    int likeCount,
    LocalDateTime createdAt
) {

  public static StoryDto from(Story story, Hero hero) {
    var likeDTOs = LikeDto.listFrom(story.getLikes());

    return new StoryDto(
        story.getId(),
        story.getHeroId(),
        story.getContent(),
        Collections.emptyList(),
        Collections.emptyList(),
        story.getAudioUrl(),
        story.getAudioDuration(),
        likeDTOs,
        likeDTOs.size(),
        story.getCreatedAt()
    );
  }
}
