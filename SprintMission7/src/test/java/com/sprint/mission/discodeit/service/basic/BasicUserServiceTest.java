package com.sprint.mission.discodeit.service.basic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.user.UserAlreadyExistsException;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BasicUserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @InjectMocks
  private BasicUserService basicUserService;

  @Test
  @DisplayName("create 성공")
  void create_success() {

    // given
    // 테스트 입력 데이터
    UserCreateRequest request = new UserCreateRequest("testUser", "test@email.com", "password");

    // 저장된 User 객체
    User savedUser = new User("testUser", "test@email.com", "password", null);

    // 반환 설정
    given(userRepository.existsByUsername("testUser")).willReturn(false);
    given(userRepository.existsByEmail("test@email.com")).willReturn(false);

    // save 호출 시 User 반환
    given(userRepository.save(any(User.class))).willReturn(savedUser);

    // 매핑 시 DTO 생성
    given(userMapper.toDto(any(User.class))).willAnswer(invocation -> {
      User userArg = invocation.getArgument(0);
      return new UserDto(userArg.getId(), userArg.getUsername(), userArg.getEmail(), null, null);
    });

    // when
    // 테스트 대상 메서드 호출
    UserDto userDto = basicUserService.create(request, Optional.empty());

    // then
    // 검증
    assertThat(userDto.username()).isEqualTo("testUser");
    verify(userRepository).existsByEmail(request.email());
    verify(userRepository).existsByUsername(request.username());
    verify(userRepository).save(any(User.class));
    verify(userMapper).toDto(any(User.class));
  }

  @Test
  @DisplayName("create 실패 - 이미 존재하는 이메일")
  void create_fail_emailExists() {
    // given
    // 중복된 이메일
    UserCreateRequest request = new UserCreateRequest("testUser", "test@email.com", "password");
    given(userRepository.existsByEmail(request.email())).willReturn(true);

    // when & then
    // 메서드 호출 시 UserAlreadyExistsException 발생
    assertThatThrownBy(() -> basicUserService.create(request, Optional.empty()))
        .isInstanceOf(UserAlreadyExistsException.class);

    // 검증
    verify(userRepository).existsByEmail(request.email());
    verify(userRepository, never()).save(any());
  }

  @Test
  @DisplayName("update 성공")
  void update_success() {
    // given
    UUID uuid = UUID.randomUUID();
    UserUpdateRequest request = new UserUpdateRequest("newUser", "new@email.com", "newPassword");

    // 기존 User
    User existingUser = new User("oldUser", "old@email.com", "oldPassword", null);

    given(userRepository.findById(uuid)).willReturn(Optional.of(existingUser));
    given(userRepository.existsByEmail(request.newEmail())).willReturn(false);
    given(userRepository.existsByUsername(request.newUsername())).willReturn(false);
    given(userMapper.toDto(existingUser)).willReturn(new UserDto(uuid, request.newUsername(), request.newEmail(), null, null));

    // when
    UserDto updatedDto = basicUserService.update(uuid, request, Optional.empty());

    // then
    assertThat(updatedDto.username()).isEqualTo(request.newUsername());
    verify(userRepository).findById(uuid);
    verify(userRepository).existsByEmail(request.newEmail());
    verify(userRepository).existsByUsername(request.newUsername());
    verify(userMapper).toDto(existingUser);
  }

  @Test
  @DisplayName("update 실패 - 유저 없음")
  void update_fail_userNotFound() {
    // given
    UUID uuid = UUID.randomUUID();
    UserUpdateRequest request = new UserUpdateRequest("newUser", "new@email.com", "newPassword");

    given(userRepository.findById(uuid)).willReturn(Optional.empty());

    // when & then
    assertThatThrownBy(() -> basicUserService.update(uuid, request, Optional.empty()))
        .isInstanceOf(UserNotFoundException.class);

    verify(userRepository).findById(uuid);

    // User가 없으므로 이후 로직은 호출되면 안 됨
    verify(userRepository, never()).existsByEmail(request.newEmail());
    verify(userRepository, never()).existsByUsername(request.newUsername());
    verify(userMapper, never()).toDto(any());
  }

  @Test
  @DisplayName("delete 성공")
  void delete_success() {
    // given
    UUID uuid = UUID.randomUUID();

    given(userRepository.existsById(uuid)).willReturn(true);
    willDoNothing().given(userRepository).deleteById(uuid);

    // when
    basicUserService.delete(uuid);

    // then
    verify(userRepository).existsById(uuid);
    verify(userRepository).deleteById(uuid);

  }

  @Test
  @DisplayName("delete 실패 - 유저 없음")
  void delete_fail_userNotFound() {
    // given
    UUID uuid = UUID.randomUUID();

    given(userRepository.existsById(uuid)).willReturn(false);

    // when & then
    assertThatThrownBy(() -> basicUserService.delete(uuid))
        .isInstanceOf(UserNotFoundException.class);

    verify(userRepository).existsById(uuid);

    // 호출되면 안 됨
    verify(userRepository, never()).deleteById(any());
  }
}
