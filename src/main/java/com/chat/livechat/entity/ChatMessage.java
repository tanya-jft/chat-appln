package com.chat.livechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage {
    private String sender;
    private String message;

    @CurrentTimestamp
    private Timestamp timestamp;

}
