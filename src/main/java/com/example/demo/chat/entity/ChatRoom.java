package com.example.withdogandcat.domain.chat.entity;

import com.example.withdogandcat.domain.shop.entity.Shop;
import com.example.withdogandcat.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatroom")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//public class ChatRoom implements Serializable {
//    private static final long serialVersionUID = 6494678977089006639L;
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 1 - user is out, 2 - shop is out
    @Column(name = "is_out")
    private Integer isOut;

    // 적절한 생성자로 변경
    public ChatRoom(Shop shop, User user) {
        this.shop = shop;
        this.user = user;
        this.roomName = roomName;
        this.isOut = 0;
    }

    // isOut 상태 업데이트 메서드
    public void isOutUpdate(int isOut){
        this.isOut = isOut;
    }
}
