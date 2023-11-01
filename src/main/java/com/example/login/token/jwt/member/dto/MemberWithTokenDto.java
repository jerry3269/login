package com.example.login.token.jwt.member.dto;

import com.example.login.global.member.domain.Member;
import lombok.Builder;

import static com.example.login.token.jwt.member.basic.JwtStaticField.BEARER;

public record MemberWithTokenDto(
        Long id,
        String username,
        String userId,
        String refreshToken
){

    @Builder
    public MemberWithTokenDto {
    }

    public static MemberWithTokenDto from(Member entity, String refreshToken) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userId(entity.getUserId())
                .refreshToken(BEARER + refreshToken).build();
    }

    public static MemberWithTokenDto withoutToken(Member entity) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userId(entity.getUserId()).build();
    }
}
