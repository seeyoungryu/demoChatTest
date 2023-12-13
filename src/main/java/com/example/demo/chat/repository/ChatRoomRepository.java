package com.example.withdogandcat.domain.chat.repository;
import com.example.withdogandcat.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByShopIdOrUserID(Long id_1, Long id_2);

    ChatRoom findChatRoomByShopIdAndUserId(Long id_1, Long id_2);

    Optional<ChatRoom> findChatRoomById(Long id);

    List<ChatRoom> findByItem_Id(Long itemId);
}