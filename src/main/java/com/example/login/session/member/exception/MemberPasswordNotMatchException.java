package com.example.login.session.member.exception;

import com.example.login.global.exception.NotMatchException;

import static com.example.login.global.error.ErrorStaticField.INVALID_PASSWORD;

public class MemberPasswordNotMatchException extends NotMatchException {

    private static final String MESSAGE = INVALID_PASSWORD;
    public MemberPasswordNotMatchException() {
        super(MESSAGE);
    }
}
