package io.itmca.lifepuzzle.domain.hero.entity;

import io.itmca.lifepuzzle.global.file.domain.HeroProfileImage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.Nullable;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Hero {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long heroNo;
  private String name;
  private String nickname;
  private LocalDate birthdate;
  private String image;

  @OneToMany(mappedBy = "hero")
  @Builder.Default
  private List<HeroUserAuth> heroUserAuths = new ArrayList<>();

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private LocalDateTime updatedAt;


  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  private Boolean isLunar;

  private String facebookUserId;

  public static Hero defaultHero() {
    return Hero.builder()
        .name("주인공")
        .nickname("소중한 분")
        .birthdate(LocalDate.of(1970, 1, 1))
        .isLunar(false)
        .image("")
        .build();
  }

  // 도메인 행위 메서드
  public void updateInfo(String name, String nickname, LocalDate birthdate, Boolean isLunar) {
    this.name = name;
    this.nickname = nickname;
    this.birthdate = birthdate;
    this.isLunar = isLunar;
  }

  public void initializeAuth(HeroUserAuth heroUserAuth) {
    this.heroUserAuths = java.util.Collections.singletonList(heroUserAuth);
  }

  public void setProfileImage(@Nullable HeroProfileImage heroProfileImage) {
    if (heroProfileImage == null) {
      this.image = null;
    } else {
      this.image = heroProfileImage.getFileName();
    }
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  public boolean isActive() {
    return this.deletedAt == null;
  }

  public void setFacebookUserId(String facebookUserId) {
    this.facebookUserId = facebookUserId;
  }
}
