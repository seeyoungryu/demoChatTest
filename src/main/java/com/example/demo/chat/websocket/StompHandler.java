package com.example.withdogandcat.domain.chat.websocket;

import
import com.example.withdogandcat.global.security.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // websocket 연결시 헤더의 jwt token 유효성 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String authorization = jwtUtil.extractJwt(accessor);

            jwtUtil.validateTokenWebsocket(authorization);
        }

        if (StompCommand.SEND == accessor.getCommand()) {
            Object payload = message.getPayload();
            log.warn("SEND");
            log.warn(objectMapper.convertValue(payload, String.class));
        }

        // websocket 연결시 헤더의 jwt token 유효성 검증
        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            Object payload = message.getPayload();
            log.warn("SUBSCRIBE");
            log.warn(objectMapper.convertValue(payload, String.class));
        }
        return message;
    }
}