package com.example.login.token.jwt.member.exception;

import com.example.login.global.exception.UnauthorizedException;

import static com.example.login.global.error.ErrorStaticField.INVALID_REFRESH_TOKEN;

public class InvalidRefreshTokenException extends UnauthorizedException {
    private static final String MESSAGE = INVALID_REFRESH_TOKEN;
    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }
}
