package com.sprint.mission.discodeit.service.basic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.user.UserAlreadyExistsException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import java.util.Optional;
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

  @Mock
  private BinaryContentRepository binaryContentRepository;

  @Mock
  private BinaryContentStorage binaryContentStorage;

  @InjectMocks
  private BasicUserService basicUserService;

  @Test
  @DisplayName("create 성공")
  void create_success() {

    UserCreateRequest request = new UserCreateRequest("testUser", "test@email.com", "password");
    User savedUser = new User("testUser", "test@email.com", "password", null);

    given(userRepository.existsByUsername("testUser")).willReturn(false);
    given(userRepository.existsByEmail("test@email.com")).willReturn(false);
    given(userRepository.save(any(User.class))).willReturn(savedUser);
    given(userMapper.toDto(any(User.class))).willAnswer(invocation -> {
      User userArg = invocation.getArgument(0);
      return new UserDto(userArg.getId(), userArg.getUsername(), userArg.getEmail(), null, null);
    });

    UserDto userDto = basicUserService.create(request, Optional.empty());

    assertThat(userDto.username()).isEqualTo("testUser");
    verify(userRepository).existsByEmail(request.email());
    verify(userRepository).existsByUsername(request.username());
    verify(userRepository).save(any(User.class));
    verify(userMapper).toDto(any(User.class));
  }

  @Test
  @DisplayName("create 실패 - 이미 존재하는 이메일")
  void create_fail_emailExists() {
    UserCreateRequest request = new UserCreateRequest("testUser", "test@email.com", "password");

    given(userRepository.existsByEmail(request.email())).willReturn(true);

    assertThatThrownBy(() -> basicUserService.create(request, Optional.empty()))
        .isInstanceOf(UserAlreadyExistsException.class);

    verify(userRepository).existsByEmail(request.email());
    verify(userRepository, never()).save(any());
  }
}
