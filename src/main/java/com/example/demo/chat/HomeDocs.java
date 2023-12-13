package com.example.withdogandcat.domain.chat;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

public interface HomeDocs {

    @Operation(
            summary = "채팅방 목록 조회",
            description = "채팅방 목록 조회"
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
    public String getChatRooms();
}
