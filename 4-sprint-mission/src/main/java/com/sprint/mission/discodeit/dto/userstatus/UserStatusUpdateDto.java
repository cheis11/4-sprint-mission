package com.sprint.mission.discodeit.dto.userstatus;

import java.util.UUID;

public record UserStatusUpdateDto(UUID id, boolean updateAccessTime) {}
