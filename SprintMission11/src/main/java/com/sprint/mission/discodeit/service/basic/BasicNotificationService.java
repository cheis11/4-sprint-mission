package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.data.NotificationDto;
import com.sprint.mission.discodeit.entity.Notification;
import com.sprint.mission.discodeit.exception.notification.NotificationNotFoundException;
import com.sprint.mission.discodeit.mapper.NotificationMapper;
import com.sprint.mission.discodeit.repository.NotificationRepository;
import com.sprint.mission.discodeit.service.NotificationService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BasicNotificationService implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationMapper notificationMapper;

  public BasicNotificationService(NotificationRepository notificationRepository,
      NotificationMapper notificationMapper) {
    this.notificationRepository = notificationRepository;
    this.notificationMapper = notificationMapper;
  }

  @Override
  public List<NotificationDto> getNotifications(UUID userId) {
    List<Notification> notifications = notificationRepository.findAllByReceiverId(userId);
    List<NotificationDto> notificationDtos = notifications.stream()
        .map(notificationMapper::toDto)
        .toList();
    return notificationDtos;
  }

  @Override
  public Optional<NotificationDto> getNotification(UUID notificationId) {
    Notification notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> NotificationNotFoundException.withId(notificationId));
    return Optional.ofNullable(notificationMapper.toDto(notification));
  }

  @Override
  public void deleteNotification(UUID notificationId) {
    notificationRepository.deleteById(notificationId);
  }
}
