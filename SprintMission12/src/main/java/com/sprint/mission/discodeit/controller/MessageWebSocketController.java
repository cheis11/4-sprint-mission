package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/messages")
  public void sendMessage(@Payload MessageCreateRequest request){
    simpMessagingTemplate.convertAndSend("/sub/messages", request);
  }
}
