package com.example.withdogandcat.domain.chat.dto;
import com.example.withdogandcat.domain.chat.ParameterNameConfig;
import com.example.withdogandcat.domain.chat.entity.ChatMessage;
import com.example.withdogandcat.domain.chat.entity.ChatRoom;
import com.example.withdogandcat.domain.chat.entity.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ChatMessageRequestDto {

    @JsonProperty(ParameterNameConfig.ChatMessage.TYPE)
    private MessageType type;
    @JsonProperty(ParameterNameConfig.ChatRoom.ID)
    private Long chatRoomId;
    @JsonProperty(ParameterNameConfig.ChatRoom.NAME)
    private String chatRoomName;
    @JsonProperty(ParameterNameConfig.ChatMessage.CHATMESSAGE)
    private String message;
    @JsonProperty(ParameterNameConfig.ChatRoom.SENDER)
    private String senderNickname;

    public ChatMessage toEntity() {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(this.chatRoomId);
        return new ChatMessage(
                chatRoom,
                this.senderNickname,
                this.message,
                this.type
        );
    }
}
