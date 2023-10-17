package com.example.login.global.member.exception;

import com.example.login.global.exception.UnauthorizedException;

import static com.example.login.global.error.ErrorStaticField.USER_UNAUTHORIZED;

public class MemberUnauthorizedException extends UnauthorizedException {

    private static final String MESSAGE = USER_UNAUTHORIZED;
    public MemberUnauthorizedException() {
        super(MESSAGE);
    }
}
