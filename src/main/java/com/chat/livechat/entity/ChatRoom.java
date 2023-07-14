package com.chat.livechat.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    @Id
    @Column(name = "room_id")
    private int id;

    @ManyToOne(targetEntity = User.class ,cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "user1", referencedColumnName = "id")
    private User user1;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user2" , referencedColumnName = "id")
    private User user2;



}
