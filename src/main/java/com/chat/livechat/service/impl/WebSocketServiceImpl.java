package com.chat.livechat.service.impl;

import com.chat.livechat.entity.*;
import com.chat.livechat.repository.ChatRoomRepo;
import com.chat.livechat.repository.MessageContainerRepo;
import com.chat.livechat.repository.UserRepo;
import com.chat.livechat.service.WebSocketService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    final private ChatRoomRepo chatRoomRepo;
    final private MessageContainerRepo messageContainerRepo;
    final private UserRepo userRepo;

    @Override
    public Boolean saveMessage(int roomId, ChatMessage message, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        log.info("principal {}", user);
        int user_id = roomId - user.getId();

        Optional<ChatRoom> isRoom = chatRoomRepo.findById(roomId); // check for room
        Optional<User> toChatWithUser = userRepo.findById(user_id); // other user id

        ChatRoom chatRoom ;

        if (isRoom.isPresent()) {
            log.info("Room is present");
            chatRoom = isRoom.get();

        } else {
            log.info("Room does not exist");
            chatRoom = ChatRoom.builder().id(roomId).user1(user).user2(toChatWithUser.get()).build();

            chatRoomRepo.save(chatRoom);
        }

        String msg = message.getMessage();

        if (!msg.equalsIgnoreCase("Join")){
            MessageContainer messageContainer = MessageContainer.builder().message(msg).roomId(chatRoom).userId(user).build();
            messageContainerRepo.save(messageContainer);
            log.info("Message container save {}", messageContainer);
        }
        return true;
    }

    @Override
    public String loadPreviousMessage(int roomId) {
        Optional<ChatRoom> chatRoom = chatRoomRepo.findById(roomId);

        SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

        if (chatRoom.isPresent()) {
            List<MessageContainer> messageContainers = messageContainerRepo.findAllByRoomIdOrderByDateTimeAsc(chatRoom.get());

            LinkedHashMap<String, List<Map<String, String>>> formatedData = messageContainers.stream().map(msg -> {
                Map<String, String> data = new HashMap<>();
                if (msg.getMessage() != null) {
                    data.put("message", msg.getMessage());
                    data.put("date", sdf1.format(msg.getDateTime()));
                    data.put("time", sdf2.format(msg.getDateTime()));
                    data.put("sender", msg.getUserId().getDisplayName());
                    data.put("username", msg.getUserId().getUsername());
                }
                return data;
            }).collect(Collectors.groupingBy(msg -> msg.get("date"), LinkedHashMap::new, Collectors.toList())); // group the msg by date
            log.info("Formated data {}", formatedData);
            // remove the date fields inside the object because date is stored as the key of the map -> {date=[{},{}]}
            formatedData.entrySet().forEach((x) -> x.getValue().forEach(obj -> obj.remove("date")));
            Gson gson = new Gson();
            String data1 = gson.toJson(formatedData);
            log.info("Date {}", data1);
            return data1;
        }

        return "";
    }
}
