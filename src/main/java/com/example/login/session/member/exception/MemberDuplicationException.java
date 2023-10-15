package com.example.login.session.member.exception;

import com.example.login.global.exception.DuplicationException;

import static com.example.login.global.error.ErrorStaticField.DUP_LOGIN_ID;

public class MemberDuplicationException extends DuplicationException {

    private static final String MESSAGE = DUP_LOGIN_ID;

    public MemberDuplicationException() {
        super(MESSAGE);
    }
}
