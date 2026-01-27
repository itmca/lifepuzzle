package io.itmca.lifepuzzle.domain.hero.endpoint;

import io.itmca.lifepuzzle.domain.auth.jwt.AuthPayload;
import io.itmca.lifepuzzle.domain.hero.endpoint.response.HeroDetailListQueryResponse;
import io.itmca.lifepuzzle.domain.hero.endpoint.response.HeroDetailQueryResponse;
import io.itmca.lifepuzzle.domain.hero.endpoint.response.HeroListQueryResponse;
import io.itmca.lifepuzzle.domain.hero.endpoint.response.HeroQueryResponse;
import io.itmca.lifepuzzle.domain.hero.service.HeroQueryService;
import io.itmca.lifepuzzle.domain.hero.service.HeroValidationService;
import io.itmca.lifepuzzle.domain.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "주인공 조회")
public class HeroQueryEndpoint {
  private final HeroQueryService heroQueryService;
  private final HeroValidationService heroValidationService;
  private final UserQueryService userQueryService;

  @Operation(summary = "주인공 전체 목록 조회 (상세 정보 포함)")
  @GetMapping({"/v1/heroes"})
  public HeroDetailListQueryResponse getHeroes(@AuthenticationPrincipal AuthPayload authPayload) {
    var user = userQueryService.findByUserNo(authPayload.getUserId());

    return heroQueryService.toDetailQueryResponses(user);
  }

  @Operation(summary = "주인공 조회 (상세 정보 포함)")
  @GetMapping({"/v1/heroes/{heroNo}"})
  public HeroDetailQueryResponse getHeroDetail(
      @PathVariable("heroNo") @Schema(description = "주인공키") Long heroNo,
      @AuthenticationPrincipal AuthPayload authPayload) {
    val userNo = authPayload.getUserId();
    heroValidationService.validateUserCanAccessHero(userNo, heroNo);

    var hero = heroQueryService.findHeroByHeroNo(heroNo);

    return heroQueryService.toDetailQueryResponse(hero, userNo);
  }

  @Operation(summary = "주인공 조회 (간단 정보)")
  @GetMapping({"/v2/heroes/{heroNo}"})
  public HeroQueryResponse getHeroSimple(
      @PathVariable("heroNo") @Schema(description = "주인공키") Long heroNo,
      @AuthenticationPrincipal AuthPayload authPayload) {
    val userNo = authPayload.getUserId();
    heroValidationService.validateUserCanAccessHero(userNo, heroNo);

    var hero = heroQueryService.findHeroByHeroNo(heroNo);

    return heroQueryService.toQueryResponse(hero, userNo);
  }

  @Operation(summary = "주인공 전체 목록 조회 (간단 정보)")
  @GetMapping({"/v2/heroes"})
  public HeroListQueryResponse getHeroesSimple(
      @AuthenticationPrincipal AuthPayload authPayload) {
    var user = userQueryService.findByUserNo(authPayload.getUserId());

    return heroQueryService.toQueryResponses(user);
  }
}
