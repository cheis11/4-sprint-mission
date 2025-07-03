package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    Message save(Message message);

    List<Message> findAll();

    Optional<Message> findById(UUID id);

    void delete(UUID id);

    List<Message> findMessagesByContentContains(String MessageName);
}
