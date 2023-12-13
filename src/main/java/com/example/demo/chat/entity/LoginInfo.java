package com.example.withdogandcat.domain.chat.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginInfo {

    private final String nickname;

    private final String token;



    @Builder
    public LoginInfo(String nickname, String token) {

        this.nickname = nickname;

        this.token = token;

    }

}

