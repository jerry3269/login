package com.example.login.global.exception;

import lombok.Getter;

import static com.example.login.global.error.ErrorStaticField.FORBIDDEN;

@Getter
public abstract class ForbiddenException extends RuntimeException{
    private static final int STATUS_CODE = FORBIDDEN;
    private String message;

    public ForbiddenException(String message) {
        this.message = message;
    }
}
