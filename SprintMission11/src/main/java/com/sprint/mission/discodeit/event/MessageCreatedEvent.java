package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Getter
@Async
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
