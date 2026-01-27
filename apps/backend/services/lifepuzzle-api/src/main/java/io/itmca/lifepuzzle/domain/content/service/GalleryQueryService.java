package io.itmca.lifepuzzle.domain.content.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import io.itmca.lifepuzzle.domain.content.endpoint.response.GalleryQueryResponse;
import io.itmca.lifepuzzle.domain.content.endpoint.response.dto.AgeGroupGalleryDto;
import io.itmca.lifepuzzle.domain.content.endpoint.response.dto.TagDto;
import io.itmca.lifepuzzle.domain.content.entity.Gallery;
import io.itmca.lifepuzzle.domain.content.repository.GalleryRepository;
import io.itmca.lifepuzzle.domain.content.type.AgeGroup;
import io.itmca.lifepuzzle.domain.hero.entity.Hero;
import io.itmca.lifepuzzle.domain.hero.service.HeroQueryService;
import io.itmca.lifepuzzle.global.exception.GalleryNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GalleryQueryService {
  private final GalleryRepository galleryRepository;
  private final HeroQueryService heroQueryService;

  public GalleryQueryResponse getHeroGallery(Long heroNo) {
    var hero = heroQueryService.findHeroByHeroNo(heroNo);
    var heroAge = calculateAge(hero.getBirthdate());
    var photos = getFilteredGallery(hero.getHeroNo(), heroAge);
    var ageGroupsDTO = getGalleryByAgeGroup(photos, hero);

    return GalleryQueryResponse.builder()
        .ageGroups(ageGroupsDTO)
        .tags(getTags(heroAge))
        .totalGallery(photos.size())
        .build();
  }

  private int calculateAge(LocalDate birthdate) {
    return (int) ChronoUnit.YEARS.between(birthdate, LocalDate.now());
  }

  private List<Gallery> getFilteredGallery(Long heroNo, int heroAge) {
    var heroAgeGroup = AgeGroup.of(heroAge);
    var allowedAgeGroups = getAgeGroupsUpTo(heroAgeGroup);

    return galleryRepository.findByHeroIdAndAgeGroupsWithStories(heroNo, allowedAgeGroups)
        .orElseThrow(() -> new GalleryNotFoundException(heroNo));
  }

  private List<AgeGroup> getAgeGroupsUpTo(AgeGroup maxAgeGroup) {
    return Arrays.stream(AgeGroup.values())
        .filter(ageGroup -> ageGroup.getRepresentativeAge() <= maxAgeGroup.getRepresentativeAge())
        .toList();
  }

  private Map<AgeGroup, AgeGroupGalleryDto> getGalleryByAgeGroup(List<Gallery> photos,
                                                                 Hero hero) {
    var groupedByAge = photos.stream()
        .collect(groupingBy(
            Gallery::getAgeGroup,
            toList()
        ));

    return AgeGroupGalleryDto.fromGroupedGallery(groupedByAge, hero.getBirthdate());
  }

  private List<TagDto> getTags(int age) {
    var heroAgeGroup = AgeGroup.of(age);

    return Arrays.stream(AgeGroup.values())
        .filter(ageGroup ->
            ageGroup == AgeGroup.UNCATEGORIZED
                || ageGroup.getRepresentativeAge() <= heroAgeGroup.getRepresentativeAge())
        .sorted(Comparator.comparingInt(AgeGroup::getRepresentativeAge))
        .map(ageGroup -> new TagDto(ageGroup, ageGroup.getDisplayName()))
        .toList();
  }
}
