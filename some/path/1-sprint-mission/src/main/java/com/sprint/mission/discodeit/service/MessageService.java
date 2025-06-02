package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    public Message addMessage(Message message);//메세지 입력
    public List<Message> getMessages();//모든 메세지 출력
    public Optional<Message> getMessage(UUID messageId);//특정 메세지 출력
    public List<Message> getMessageContains(String MessageName);//특정 단어가 포함된 메세지 출력
    public void updateMessage(UUID MessageId, String updatedText);//메세지 수정
    public void deleteMessage(Message message);//메세지 삭제
}
