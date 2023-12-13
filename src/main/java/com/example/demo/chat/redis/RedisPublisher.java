package com.example.withdogandcat.domain.chat.redis;//package com.example.demo.chat.redis;
//
//import com.example.demo.chat.entity.ChatMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class RedisPublisher {
//    private final RedisTemplate<String, Object> redisChatRoomTemplate;
//
//    public void publish(ChannelTopic topic, ChatMessage message) {
//        redisChatRoomTemplate.convertAndSend(topic.getTopic(), message);
//    }
//}