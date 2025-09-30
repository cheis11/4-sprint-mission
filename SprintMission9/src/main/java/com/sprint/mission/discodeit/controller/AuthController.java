package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.security.DiscodeitUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  @GetMapping("/me")
  public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal DiscodeitUserDetails discodeitUserDetails) {
    UserDto userDto = discodeitUserDetails.getUserDto();
    return ResponseEntity.ok(userDto);
  }
}
