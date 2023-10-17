package com.example.login.global.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginForm (
        @Email String userId,
        @NotBlank String password
){
}
