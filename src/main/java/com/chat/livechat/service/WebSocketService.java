package com.chat.livechat.service;


import com.chat.livechat.entity.ChatMessage;
import org.springframework.security.core.Authentication;

public interface WebSocketService {

    public Boolean saveMessage(int roomId, ChatMessage chatMessage, Authentication auth);
    public String loadPreviousMessage(int roomId);
}
