package com.example.withdogandcat.domain.chat.entity;

import com.example.withdogandcat.domain.chat.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.example.withdogandcat.domain.chat.dto.ChatMessageRequestDto;
@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Component
public class ChatMessage extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type; // 메시지 타입

    private String sender; // 메시지 보낸사람

    private String message; // 메시지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    private Long roomId;

    public ChatMessage(ChatRoom chatRoom, String sender, String message, MessageType type){
        this.chatRoom = chatRoom;
        this.roomId = chatRoom.getId();
        this.sender = sender;
        this.message = message;
        this.type = type;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public void setMessage(String message){
        this.message=message;
    }
}