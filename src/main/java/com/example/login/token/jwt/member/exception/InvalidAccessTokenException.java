package com.example.login.token.jwt.member.exception;

import com.example.login.global.exception.UnauthorizedException;

import static com.example.login.global.error.ErrorStaticField.INVALID_ACCESS_TOKEN;

public class InvalidAccessTokenException extends UnauthorizedException {
    private static final String MESSAGE = INVALID_ACCESS_TOKEN;
    public InvalidAccessTokenException() {
        super(MESSAGE);
    }
}
