package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.Notification;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.exception.user.UserNotFoundException;
import com.sprint.mission.discodeit.repository.NotificationRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class NotificationRequiredEventListener {

  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;
  private final ReadStatusRepository readStatusRepository;

  public NotificationRequiredEventListener(NotificationRepository notificationRepository,
      UserRepository userRepository,
      ReadStatusRepository readStatusRepository) {
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
    this.readStatusRepository = readStatusRepository;
  }

  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void on(MessageCreatedEvent event) {
    log.debug("메시지 알림 이벤트 시작, 스레드명: {}", Thread.currentThread().getName());

    String title = event.getAuthor().getUsername() + "(#" + event.getChannel().getName()+")";
    if (event.getChannel().getType() == ChannelType.PRIVATE){
      title = event.getAuthor().getUsername();
    }

    List<ReadStatus> readStatuses = readStatusRepository
        .findAllByChannelIdWithUser(event.getChannel().getId()).stream()
        .filter(ReadStatus::isNotificationEnabled)
        .toList();


    List<User> users = readStatuses.stream().map(ReadStatus::getUser).toList();

    for (User user : users) {
      if (!user.getId().equals(event.getAuthor().getId())) {
        Notification notification = new Notification(user, title, event.getContent());
        notificationRepository.save(notification);
        log.debug("메시지 알림 저장 완료");
      }
    }
    log.info("NotificationRequiredEventListener class: {}", this.getClass());
  }

  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void on(RoleUpdatedEvent event) {
    log.debug("권한 변경 알림 이벤트 시작");
    String title = "권한이 변경되었습니다.";
    User user = userRepository.findById(event.getUserId())
        .orElseThrow(() -> UserNotFoundException.withId(event.getUserId()));
    String content = event.getRole() + " -> " + event.getNewRole();
    Notification notification = new Notification(user,title,content);
    notificationRepository.save(notification);
    log.debug("권한 변경 알림 이벤트 완료");
  }
}
