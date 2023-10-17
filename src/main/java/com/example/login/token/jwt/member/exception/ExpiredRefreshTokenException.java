package com.example.login.token.jwt.member.exception;

import com.example.login.global.exception.UnauthorizedException;

import static com.example.login.global.error.ErrorStaticField.EXPIRED_REFRESH_TOKEN;

public class ExpiredRefreshTokenException extends UnauthorizedException {
    private static final String MESSAGE = EXPIRED_REFRESH_TOKEN;
    public ExpiredRefreshTokenException() {
        super(MESSAGE);
    }
}
