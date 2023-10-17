package com.example.login.token.jwt.member.exception;

import com.example.login.global.exception.UnauthorizedException;

public class ExpiredAccessTokenException extends UnauthorizedException {
    public ExpiredAccessTokenException(String message) {
        super(message);
    }
}
