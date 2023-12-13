package com.example.withdogandcat.domain.chat.service;

import com.example.withdogandcat.domain.chat.dto.ChatRoomResponseDto;
import com.example.withdogandcat.domain.chat.entity.ChatRoom;
import com.example.withdogandcat.domain.chat.redis.RedisSubscriber;
import com.example.withdogandcat.domain.chat.repository.ChatRoomRepository;
import com.example.withdogandcat.domain.chat.repository.RedisRepository;
import com.example.withdogandcat.domain.shop.ShopRepository;
import com.example.withdogandcat.domain.shop.entity.Shop;
import com.example.withdogandcat.domain.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final RedisRepository redisRepository;
private final ShopRepository shopRepository;
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;

    // Redis
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    Map<String, ChannelTopic> topics = new HashMap<>();

    // 채팅방 생성
    public ChatRoomResponseDto createChatRoom(Long shopId, User user) {
        Shop shop = shopRepository.findShopById(shopId); // shop 정보 조회
        Long userId = user.getUserId();

        // 이미 존재하는 채팅방인지 확인
        if (chatRoomRepository.findAllByShopIdOrUserID(shopId, userId) == null) {
            // 새로운 채팅방 생성
            ChatRoom chatRoom = new ChatRoom(shop,user);
            chatRoomRepository.save(chatRoom);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ChatRoomResponseDto responseDto = new ChatRoomResponseDto(chatRoom);
                String chatRoomRedis = objectMapper.writeValueAsString(responseDto);
                redisRepository.save("chatroom:shop" + shop.getShopId(), chatRoomRedis); // Redis 저장 키 값을 shop 기반으로 변경
                return responseDto;
            } catch (JsonProcessingException e) {
                throw new RuntimeException("객체를 String으로 변환하지 못했습니다.");
            }
        } else {
            // 이미 존재하는 채팅방 반환
            ChatRoom chatRoom = chatRoomRepository.findChatRoomByShopIdAndUserId(shopId, userId);
            return new ChatRoomResponseDto(chatRoom);
        }
    }



    // 채팅방 단일 조회
    public ChatRoom getRoom(Long roomId,User user) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(roomId).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );

        ChannelTopic topic = topics.get(roomId.toString());
        if (topic == null) {
            topic = new ChannelTopic(roomId.toString());
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId.toString(), topic);
        }
        return chatRoom;
    }



    // 채팅방 삭제
    @Transactional
    public List<ChatRoomResponseDto> deleteChatRoom(Long roomId, User user){
        Long id = user.getUserId();
        ChatRoom deleteChatRoom = chatRoomRepository.findChatRoomById(roomId).orElseThrow(() ->
                new IllegalArgumentException("선택한 채팅방은 존재하지 않습니다.")
        );
        int isOut = deleteChatRoom.getIsOut();

        if(isOut == 0){
            List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllByShopIdOrUserID(id, id)
                    .stream()
                    .filter(chatRoom -> !chatRoom.getId().equals(roomId))
                    .map(chatRoom -> new ChatRoomResponseDto(chatRoom))
                    .toList();

            if (id.equals(deleteChatRoom.getUser().getUserId())) { // 빠진 닫는 괄호 추가
                isOut = 1;
            } else {
                isOut = 2;
            }
            deleteChatRoom.isOutUpdate(isOut);
            return chatRoomList;
        }
        else{
            chatRoomRepository.delete(deleteChatRoom);

            List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllByShopIdOrUserID(id, id)
                    .stream()
                    .map(chatRoom -> new ChatRoomResponseDto(chatRoom))
                    .toList();
            return chatRoomList;
        }
    }

    public ChannelTopic getTopic(Long roomId) {
        return topics.get(roomId.toString());
    }

    //유저별 전체 채팅방 조회
    public List<ChatRoomResponseDto> getAllChatRooms(User user) {
        Long id = user.getUserId();

        List<ChatRoomResponseDto> chatRoomList = chatRoomRepository.findAllByShopIdOrUserID(id, id)
                .stream()
                .filter(chatRoom -> {
                    if (id.equals(chatRoom.getShop().getShopId()) && chatRoom.getIsOut() == 1){
                        return false;
                    }
                    else if (id.equals(chatRoom.getUser().getUserId()) && chatRoom.getIsOut() == 2){
                        return false;
                    }
                    else {
                        return true;
                    }
                })
                .map(chatRoom -> new ChatRoomResponseDto(chatRoom))
                .toList();

        return chatRoomList;
    }


//    private void validateOwnerShipOfItem(Long itemId, Member member) {
//        Item item = itemRepository.findById(itemId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
//
//        Optional.of(item)
//                .map(Item::getShop)
//                .map(Shop::getMember)
//                .map(Member::getId)
//                .filter(id -> id.equals(member.getId()))
//                .orElseThrow(() -> new IllegalArgumentException("해당 상품의 소유주만 열람할 수 있습니다."));
//    }
}