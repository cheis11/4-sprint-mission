package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.message.MessageCreateDto;
import com.sprint.mission.discodeit.dto.message.MessageResponseDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MessageMapper {

    public Message messageCreateDtoToMessage(MessageCreateDto messageCreateDto) {
        return new Message(
                messageCreateDto.content(), messageCreateDto.userId(), messageCreateDto.channelId());
    }

    public MessageResponseDto messageToMessageResponseDto(Message message) {
        return new MessageResponseDto(
                message.getId(),
                message.getChannelId(),
                message.getAuthorId(),
                message.getContents(),
                message.getState(),
                message.getAttachmentIds());
    }
}
