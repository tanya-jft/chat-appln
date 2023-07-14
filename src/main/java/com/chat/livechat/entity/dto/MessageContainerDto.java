package com.chat.livechat.entity.dto;


import com.chat.livechat.entity.User;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageContainerDto {

    private int id;
    private String message;
    private Timestamp dateTime;
    private User userId;
}
