package com.example.withdogandcat.domain.chat.controller;

import com.example.demo.chat.dto.ChatRoomResponseDto;
import com.example.demo.chat.entity.LoginInfo;
import com.example.demo.chat.service.ChatRoomService;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.security.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController{
    private final ChatRoomService chatRoomService;
    private final JwtUtil jwtUtil;

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().nickname(name).token(jwtUtil.createToken(name, UserRoleEnum.USER)).build();
    }

     //모든 채팅방 목록 반환 - 유저별
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> getAllChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.getAllChatRooms(userDetails.getMember());
    }

    // 채팅방 생성 - 아이템 상세 페이지 -> 채팅하기 버튼 누르면 실행
    @PostMapping("/room/{itemId}")
    @ResponseBody
    public ChatRoomResponseDto createRoom(@PathVariable Long itemId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.createChatRoom(itemId, userDetails.getMember());
    }

    // 채팅방 나가기
    @DeleteMapping("/room/{roomId}")
    @ResponseBody
    public List<ChatRoomResponseDto> deleteRoom(@PathVariable Long roomId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        return chatRoomService.deleteChatRoom(roomId, userDetails.getMember());
    }
}