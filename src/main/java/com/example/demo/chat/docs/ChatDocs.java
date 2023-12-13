//package com.example.withdogandcat.domain.chat.docs;
//
//import com.example.withdogandcat.domain.chat.entity.ChatMessage;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
//
//@Tag(
//        name = "CHAT API",
//        description = "CHAT API"
//)
//public interface ChatDocs {
//    @Operation(
//            summary = "메시지 보내기",
//            description = """
//                    메시지 보내기
//                    """
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "정상 작동"
//    )
//    @ApiResponse(
//            responseCode = "404",
//            description = " 해당 채팅방은 존재하지 않음."
//    )
//    @ApiResponse(
//            responseCode = "500",
//            description = "서버 에러"
//    )
//    @ApiResponse(
//            responseCode = "403",
//            description = "권한 없음"
//    )
//    public void message(ChatMessage message);
//}
