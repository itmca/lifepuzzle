package io.itmca.lifepuzzle.domain.hero.endpoint.response;

import io.itmca.lifepuzzle.domain.hero.endpoint.response.dto.HeroUserAuthQueryDto;
import io.itmca.lifepuzzle.domain.hero.entity.Hero;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Schema(title = "주인공 상세 조회 응답")
public record HeroDetailQueryResponse(
    @Schema(description = "주인공 정보") HeroQueryResponse hero,
    @Schema(description = "연결된 사용자 목록") List<HeroUserAuthQueryDto> users,
    @Schema(description = "맞춘 퍼즐 개수") int puzzleCnt
) {

  public static HeroDetailQueryResponse from(Hero hero, Long userNo, int puzzleCnt) {
    var mainUserHeroAuthQueryDTO = hero.getHeroUserAuths().stream()
        .filter(heroUserAuth -> heroUserAuth.getUser().getId().equals(userNo))
        .map(HeroUserAuthQueryDto::from)
        .toList();
    var otherUserHeroAuthQueryDTOs = hero.getHeroUserAuths().stream()
        .filter(heroUserAuth -> !heroUserAuth.getUser().getId().equals(userNo))
        .map(HeroUserAuthQueryDto::from)
        .sorted(Comparator.comparing(HeroUserAuthQueryDto::nickName))
        .toList();

    var heroAuthQueryDTOs = new ArrayList<HeroUserAuthQueryDto>();
    heroAuthQueryDTOs.addAll(mainUserHeroAuthQueryDTO);
    heroAuthQueryDTOs.addAll(otherUserHeroAuthQueryDTOs);

    return new HeroDetailQueryResponse(
        HeroQueryResponse.from(hero, userNo),
        heroAuthQueryDTOs,
        puzzleCnt
    );
  }
}
