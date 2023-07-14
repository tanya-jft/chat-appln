package com.chat.livechat.repository;


import com.chat.livechat.entity.ChatRoom;
import com.chat.livechat.entity.MessageContainer;
import com.chat.livechat.entity.MessageContainerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageContainerRepo extends JpaRepository<MessageContainer, MessageContainerId> {

    public List<MessageContainer> findAllByRoomIdOrderByDateTimeAsc(ChatRoom chatRoom);
}
