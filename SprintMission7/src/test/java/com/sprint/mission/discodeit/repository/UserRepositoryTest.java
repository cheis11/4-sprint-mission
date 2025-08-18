package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("findAllWithProfileAndStatus 성공 - 프로필과 상태가 있는 유저는 조회된다")
  void findAllWithProfileAndStatus_success() {
    // given
    BinaryContent profile = new BinaryContent("test.png", 1024L, "image/png");
    User user = new User("test", "test@email.com", "testPass", profile);
    UserStatus status = new UserStatus(user, Instant.now());

    userRepository.save(user);

    // when
    List<User> result = userRepository.findAllWithProfileAndStatus();

    // then
    assertThat(result).hasSize(1);
    User foundUser = result.get(0);

    assertThat(foundUser.getProfile()).isNotNull();
    assertThat(foundUser.getProfile().getFileName()).isEqualTo("test.png");

    assertThat(foundUser.getStatus()).isNotNull();
    assertThat(foundUser.getStatus().isOnline()).isTrue();
  }

  @Test
  @DisplayName("findAllWithProfileAndStatus - 실패 (저장된 유저 없음)")
  void testFindAllWithProfileAndStatusFail() {
    // given: 저장된 User 없음

    // when: 커스텀 쿼리 호출
    List<User> result = userRepository.findAllWithProfileAndStatus();

    // then: 결과 검증
    assertThat(result).isEmpty(); // 결과가 없어야 함
  }

  @Test
  @DisplayName("findAllWithProfileAndStatus - 실패 (status 없음)")
  void testFindAllWithStatusMissing() {
    // given
    BinaryContent profile = new BinaryContent("profile.png", 50L, "image/png");
    User userWithProfileOnly = new User("user2", "user2@example.com", "password123", profile);
    userRepository.save(userWithProfileOnly);

    // when
    List<User> result = userRepository.findAllWithProfileAndStatus();

    // then
    assertThat(result).isEmpty();
  }

  @Test
  @DisplayName("findAllWithProfileAndStatus - 실패 (profile 없음)")
  void testFindAllWithProfileMissing() {
    // given
    User userWithStatusOnly = new User("user3", "user3@example.com", "password123", null);
    UserStatus status = new UserStatus(userWithStatusOnly, Instant.now());
    userRepository.save(userWithStatusOnly);

    // when
    List<User> result = userRepository.findAllWithProfileAndStatus();

    // then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getProfile()).isNull();
    assertThat(result.get(0).getStatus()).isNotNull();
  }

  @Test
  @DisplayName("페이징 + 정렬 테스트")
  void testPagingAndSorting() {
    // given : 다수 사용자 저장
    for (int i = 0; i < 5; i++) {
      userRepository.save(new User("user" + i, "email" + i + "@test.com", "pass", null));
    }

    // when : 페이지 0, 크기 3, username 오름차순 정렬
    Pageable pageable = PageRequest.of(0, 3, Sort.by("username").ascending());
    Page<User> page = userRepository.findAll(pageable);

    // then : 결과 검증
    assertThat(page.getContent().size()).isEqualTo(3);               // 페이지 크기 확인
    assertThat(page.getContent().get(0).getUsername()).isEqualTo("user0"); // 정렬 확인
    // 설명: findAll(Pageable)은 Spring Data JPA에서 자동으로 페이징/정렬 지원
  }
}
