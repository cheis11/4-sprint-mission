package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFMessageService implements MessageService {
    private final List<Message> data;

    public JCFMessageService() {
        this.data = new ArrayList<>();
    }

    @Override
    public Message addMessage(Message message){
        data.add(message);
        return message;
    }

    @Override
    public List<Message> getMessages() {
        return data.stream()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Message> getMessage(UUID messageId) {
        return data.stream()
                .filter(message -> message.getMessageId().equals(messageId))
                .findFirst();
    }

    @Override
    public List<Message> getMessageContains(String MessageContents) {
        return data.stream()
                .filter(message -> message.getContent().contains(MessageContents))
                .collect(Collectors.toList());
    }

    @Override
    public void updateMessage(UUID messageId,String updatedText) {
        data.stream()
                .filter(message -> message.getMessageId().equals(messageId))
                .findFirst()
                .ifPresent(message -> {
                    message.setContents(updatedText);
                    message.setUpdatedAt();
                });
    }

    @Override
    public void deleteMessage(Message message) {
        data.remove(message);
    }

}
