package com.chat.livechat.controller;

import com.chat.livechat.entity.ChatMessage;
import com.chat.livechat.repository.ChatRoomRepo;
import com.chat.livechat.repository.MessageContainerRepo;
import com.chat.livechat.repository.UserRepo;
import com.chat.livechat.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class WebSocketController {

    @Autowired
    private SimpMessageSendingOperations messageSending;

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/send-to-broker/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable int roomId, @Payload ChatMessage message, Authentication auth) {
        log.info("roomId {}", roomId);
        Boolean val = webSocketService.saveMessage(roomId, message, auth);

        // send to broker -> eg - /midway/101
        messageSending.convertAndSend("/midway/" + roomId, message);
    }

    @SubscribeMapping("/{roomId}")
    public String loadMessage(@DestinationVariable int roomId) {
        return webSocketService.loadPreviousMessage(roomId);
    }

    @MessageMapping("/send-to-broker/{roomId}/addUser")
    public void addUser(@DestinationVariable int roomId, @Payload ChatMessage message, Authentication auth) {
        Boolean val = webSocketService.saveMessage(roomId, message, auth);
        log.info("add user");
        messageSending.convertAndSend("/midway/" + roomId, message);
    }

}

