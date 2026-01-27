package io.itmca.lifepuzzle.domain.hero.endpoint.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(title = "주인공 상세 목록 조회 응답")
public record HeroDetailListQueryResponse(
    @Schema(description = "주인공 목록") List<HeroDetailQueryResponse> heroes
) {}
