package com.example.withdogandcat.domain.chat.docs;

import com.example.withdogandcat.domain.chat.dto.ChatRoomResponseDto;
import com.example.withdogandcat.domain.chat.entity.ChatRoom;
import com.example.withdogandcat.global.security.impl.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

@Tag(
        name = "채팅방 API",
        description = "채팅방 API")
public interface ChatRoomDocs {
    @Operation(
            summary = "모든 채팅방 목록 반환 - 유저별",
            description = "모든 채팅방 목록 반환 - 유저별"
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "서버 에러",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "권한 없음",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            )
    )
    public List<ChatRoomResponseDto> getAllChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(
            summary = "채팅방 생성 - 아이템 상세 페이지 -> 채팅하기 버튼 누르면 실행",
            description = "채팅방 생성 - 아이템 상세 페이지 -> 채팅하기 버튼 누르면 실행"
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            , schema = @Schema(implementation = ChatRoomResponseDto.class)
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "서버 에러",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "권한 없음",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            )
    )
    public ChatRoom createRoom(@PathVariable Long itemId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(
            summary = "채팅방 상세 조회",
            description = "채팅방 상세 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            , schema = @Schema(implementation = ChatRoomResponseDto.class)
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "서버 에러",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "403",
            description = "권한 없음",
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json"
            )
    )
    public ChatRoomResponseDto getChatRoomDetails(@PathVariable String roomId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails);

    @Operation(
            summary = "채팅방 입장 화면",
            description = "채팅방 입장 화면"
    )
    @ApiResponse(
            responseCode = "200",
            description = "정상 작동"
    )
    @ApiResponse(
            responseCode = "500",
            description = "서버 에러"
    )
    @ApiResponse(
            responseCode = "403",
            description = "권한 없음"
    )
    public String roomDetail(Model model, @PathVariable String roomId);

}
