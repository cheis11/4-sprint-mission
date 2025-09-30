package com.sprint.mission.discodeit.security;


import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscodeitUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final DiscodeitAuthorityUtils authorityUtils;
  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findByEmail(email);
    User findUser = optionalUser.orElseThrow(UserNotFoundException::new);

    UserDto userDto = userMapper.toDto(findUser);
    Collection<? extends GrantedAuthority> authorities =
        authorityUtils.createAuthorities(findUser.getEmail());

    return new DiscodeitUserDetails(userDto, findUser.getPassword(), authorities);
  }
}
