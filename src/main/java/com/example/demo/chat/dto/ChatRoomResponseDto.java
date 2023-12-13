package com.example.withdogandcat.domain.chat.dto;

import com.example.withdogandcat.domain.chat.entity.ChatRoom;
import com.example.withdogandcat.domain.chat.ParameterNameConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {

    @Schema(description = "채팅방 ID", example = "1")
    @JsonProperty(ParameterNameConfig.ChatRoom.ID)
    private Long roomId;

    @Schema(description = "채팅방 이름", example = "퍼피토크")
    @JsonProperty(ParameterNameConfig.ChatRoom.NAME)
    private String roomName;

    @Schema(description = "애견샵 ID", example = "10")
    @JsonProperty(ParameterNameConfig.Shop.ID)
    private Long shopId;

    @Schema(description = "애견샵 이름", example = "해피펫스토어")
    @JsonProperty(ParameterNameConfig.Shop.NAME)
    private String shopName;

    @Schema(description = "주인 ID", example = "100")
    @JsonProperty(ParameterNameConfig.Member.ID)
    private Long ownerId;

    @Schema(description = "주인 닉네임", example = "강아지사랑")
    @JsonProperty(ParameterNameConfig.Member.NICKNAME)
    private String ownerNickname;

    // 채팅방 생성자
    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getId();
        this.roomName = chatRoom.getRoomName();
        this.shopId = chatRoom.getShop().getShopId();
        this.shopName = chatRoom.getShop().getShopName();
        this.ownerId = chatRoom.getUser().getUserId();
        this.ownerNickname = chatRoom.getUser().getNickname();
    }
}
