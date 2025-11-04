package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.dto.data.MessageDto;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.Async;

@Getter
@Async
public class MessageCreatedEvent extends ApplicationEvent {

  private final MessageDto data;

  public MessageCreatedEvent(Object source, MessageDto data) {
    super(source);
    this.data = data;
  }
}
