package com.chat.livechat.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
public class MessageContainerId implements Serializable {

    private User userId;

    private ChatRoom roomId;


}
