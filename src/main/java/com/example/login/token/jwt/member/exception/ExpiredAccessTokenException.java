package com.example.login.token.jwt.member.exception;

import com.example.login.global.exception.UnauthorizedException;

import static com.example.login.global.error.ErrorStaticField.EXPIRED_ACCESS_TOKEN;

public class ExpiredAccessTokenException extends UnauthorizedException {
    public static final String MESSAGE = EXPIRED_ACCESS_TOKEN;

    public ExpiredAccessTokenException() {
        super(MESSAGE);
    }
}
