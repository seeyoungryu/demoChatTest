package com.example.withdogandcat.domain.chat.controller;

import com.example.demo.chat.dto.ChatMessageRequestDto;
import com.example.demo.chat.dto.ChatMessageResponseDto;
import com.example.demo.chat.service.ChatMessageService;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    // 이전 메세지 전송
    @GetMapping("/message/{roomId}")
    public List<ChatMessageResponseDto> loadMessages(@PathVariable Long roomId) {
        return chatMessageService.loadMessages(roomId);
    }

    // 메세지 전송
    @MessageMapping("/chat/message")
    public ChatMessageResponseDto sendMessage(ChatMessageRequestDto message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatMessageService.sendMessages(message, userDetails.getMember());
    }
}