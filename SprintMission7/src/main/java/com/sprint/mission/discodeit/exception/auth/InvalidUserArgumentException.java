package com.sprint.mission.discodeit.exception.auth;

import com.sprint.mission.discodeit.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;

public class InvalidUserArgumentException extends AuthException {

  public InvalidUserArgumentException(Map<String, Object> details) {
    super(Instant.now(), ErrorCode.INVALID_USER_ARGUMENT, details);
  }
}
