package com.example.withdogandcat.domain.chat.dto;
import com.example.withdogandcat.domain.chat.ParameterNameConfig;
import com.example.withdogandcat.domain.chat.entity.ChatMessage;
import com.example.withdogandcat.domain.chat.entity.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageResponseDto {
    @JsonProperty(ParameterNameConfig.ChatRoom.ID)
    private Long roomId;
    @JsonProperty(ParameterNameConfig.ChatRoom.SENDER)
    private String sender;
    @JsonProperty(ParameterNameConfig.ChatMessage.CHATMESSAGE)
    private String message;
    @JsonProperty(ParameterNameConfig.ChatMessage.TYPE)
    private MessageType type;
    @JsonProperty(ParameterNameConfig.ChatMessage.CREATED_AT)
    private LocalDateTime created_at;
    @JsonProperty(ParameterNameConfig.Shop.SELLER_SHOP_NAME)
    private String sellerShopName;
    @JsonProperty(ParameterNameConfig.Shop.CONSUMER_SHOP_NAME)
    private String consumerShopName;

    public ChatMessageResponseDto(ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.type = chatMessage.getType();
        this.created_at = chatMessage.getCreatedAt();
    }
}