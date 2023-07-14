package com.chat.livechat.repository;


import com.chat.livechat.entity.ChatRoom;
import com.chat.livechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepo extends JpaRepository<ChatRoom, Integer> {
    Optional<ChatRoom> findByUser1AndUser2(User user1, User user2);
}
