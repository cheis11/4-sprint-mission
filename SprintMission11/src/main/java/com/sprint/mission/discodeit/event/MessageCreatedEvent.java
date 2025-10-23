package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageCreatedEvent extends ApplicationEvent {

  private final User author;
  private final Channel channel;
  private final String content;
  public MessageCreatedEvent(Object source, User author, Channel channel, String content) {
    super(source);
    this.author = author;
    this.channel = channel;
    this.content = content;
  }
}
