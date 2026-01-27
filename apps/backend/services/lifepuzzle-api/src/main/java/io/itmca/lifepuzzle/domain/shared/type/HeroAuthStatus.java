package io.itmca.lifepuzzle.domain.shared.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Hero에 대한 사용자 권한 수준을 정의합니다.
 * hero와 user 도메인 모두에서 사용되는 공유 타입입니다.
 */
@Getter
public enum HeroAuthStatus {
  @Schema(description = "이야기 뷰어 권한")
  VIEWER(1),
  @Schema(description = "댓글 작성 권한")
  COMMENTER(2),
  @Schema(description = "이야기 작성 권한")
  WRITER(3),
  @Schema(description = "관리자 권한")
  ADMIN(4),
  @Schema(description = "주인공 소유자")
  OWNER(5);

  public final int priority;

  HeroAuthStatus(int priority) {
    this.priority = priority;
  }
}
