package com.chat.livechat.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    @Column(name = "date_time")
    @CurrentTimestamp
    private Timestamp dateTime;


    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User userId;

    @ManyToOne(targetEntity = ChatRoom.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "chatroom_id", referencedColumnName = "room_id")
    @JsonBackReference
    private ChatRoom roomId;
}
