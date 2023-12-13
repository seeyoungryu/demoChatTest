package com.example.withdogandcat.domain.chat.service;

import com.example.withdogandcat.domain.chat.dto.ChatMessageRequestDto;
import com.example.withdogandcat.domain.chat.dto.ChatMessageResponseDto;
import com.example.withdogandcat.domain.chat.entity.ChatMessage;
import com.example.withdogandcat.domain.chat.entity.ChatRoom;
import com.example.withdogandcat.domain.chat.entity.MessageType;
import com.example.withdogandcat.domain.chat.repository.ChatMessageRepository;
import com.example.withdogandcat.domain.chat.repository.ChatRoomRepository;
import com.example.withdogandcat.domain.user.UserRepository;
import com.example.withdogandcat.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageService {
    private final RedisTemplate<String, ChatMessage> redisMessageTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChannelTopic channelTopic;
    private final NotificationService notificationService;

    // 새 메세지 전송 및 저장
    @Transactional
    public ChatMessageResponseDto sendMessages(ChatMessageRequestDto requestDto, User user) {
        ChatMessage message = requestDto.toEntity();

        User sender = userRepository.findById(message.getSender()).orElseThrow(() ->
                new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );

        Long id = sender.getId();

        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(message.getRoomId()).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );

        if((id.equals(chatRoom.getShop().getShopId()) && chatRoom.getIsOut() == 1) ||
           (id.equals(chatRoom.getUser().getUserId()) && chatRoom.getIsOut() == 2)){
            chatRoom.isOutUpdate(0);
        }

        if((id.equals(chatRoom.getShop().getShopId()) && chatRoom.getIsOut() == 2) ||
           (id.equals(chatRoom.getUser().getUserId()) && chatRoom.getIsOut() == 1)){
            chatRoom.isOutUpdate(0);
        }

        chatMessageRepository.save(message);

        // 1. 직렬화
        redisMessageTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));

        // 2. redis 저장
        redisMessageTemplate.opsForList().rightPush("chatMessages::" + message.getRoomId(), message);

        // 3. expire 을 이용해서, Key 를 만료시킬 수 있음
        // redisTemplateMessage.expire(message.getRoomId(), 1, TimeUnit.MINUTES);

        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisMessageTemplate.convertAndSend(channelTopic.getTopic(), message);

        User receiver = new User();

        if(id.equals(chatRoom.getShop().getShopId())){
            receiver = chatRoom.getConsumer();
        }
        else{
            receiver = chatRoom.getSeller();
        }

        if(message.getType().equals(MessageType.TALK)){
            URL imageUrl = sender.getImage();
            String content = sender.getShop().getShopName() + "|||" + imageUrl + "|||" + message.getMessage();
            String url = "/chat/message";
            notificationService.send(receiver, NotificationType.CHAT, content, url);
        }

        return new ChatMessageResponseDto(message);
    }

    // 이전 메세지 로드
    // @Cacheable(value = "chatMessages", key = "#roomId")
    public List<ChatMessageResponseDto> loadMessages(Long roomId) {
        List<ChatMessageResponseDto> mysqlResponseDtoList = chatMessageRepository.findAllByRoomId(roomId)
                .stream()
                .limit(100)
                .map(ChatMessageResponseDto::new)
                .toList();

        Long redisChatMessageCount = redisMessageTemplate.opsForList().size("chatMessages::" + roomId);
        int mysqlChatMessageCount = mysqlResponseDtoList.size();

        if (redisChatMessageCount == null) {
            redisChatMessageCount = 0L;
        }

        // redis 에 데이터가 있을 때
        if(redisChatMessageCount != 0){
            // redis 데이터랑 mysql 데이터랑 같을 때 + redis 데이터만 100개 이상일 때는 redis 만
            if(redisChatMessageCount == mysqlChatMessageCount || redisChatMessageCount >= 100){
                List<ChatMessage> chatMessageList = redisMessageTemplate.opsForList().range("chatMessages::" + roomId, 0, 99);
                List<ChatMessageResponseDto> chatMessageResponseDtoList = new ArrayList<>();
                for (ChatMessage chatMessage : chatMessageList) {
                    chatMessageResponseDtoList.add(new ChatMessageResponseDto(chatMessage));
                }
                return chatMessageResponseDtoList;
            }
            // redis 데이터랑 mysql 데이터랑 다를 때
            else{
                List<ChatMessageResponseDto> chatMessageResponseDtoList = chatMessageRepository.findAllByRoomId(roomId)
                        .stream()
                        .limit(100 - redisChatMessageCount)
                        .map(ChatMessageResponseDto::new)
                        .collect(Collectors.toList());

                List<ChatMessage> chatMessageList = redisMessageTemplate.opsForList().range("chatMessages::" + roomId, 0, redisChatMessageCount);
                for (ChatMessage chatMessage : chatMessageList) {
                    chatMessageResponseDtoList.add(new ChatMessageResponseDto(chatMessage));
                }
                return chatMessageResponseDtoList;
            }
        }
        // redis 에 데이터가 아예 없을 때
        else{
            List<ChatMessageResponseDto> chatMessageResponseDtoList = chatMessageRepository.findAllByRoomId(roomId)
                    .stream()
                    .map(ChatMessageResponseDto::new)
                    .toList();

            return chatMessageResponseDtoList;
        }
    }
}