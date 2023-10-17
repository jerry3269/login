package com.example.login.global.member.exception;

import com.example.login.global.exception.NotFoundException;

import static com.example.login.global.error.ErrorStaticField.USER_NOT_FOUND;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = USER_NOT_FOUND;
    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
