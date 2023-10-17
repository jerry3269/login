package com.example.login.token.jwt.member.domain;

import lombok.Getter;

public record RefreshToken(String refreshToken) {
    public static RefreshToken of(String refreshToken){
        return new RefreshToken(refreshToken);
    }
}
