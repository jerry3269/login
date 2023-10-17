package com.example.login.token.jwt.member.dto;

import com.example.login.global.member.domain.Member;
import lombok.Builder;

public record MemberWithTokenDto(
        Long id,
        String username,
        String userId,
        String accessToken
){

    @Builder
    public MemberWithTokenDto {
    }

    public static MemberWithTokenDto from(Member entity, String accessToken) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userId(entity.getUserId())
                .accessToken(accessToken).build();
    }

    public static MemberWithTokenDto withoutToken(Member entity) {
        return MemberWithTokenDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userId(entity.getUserId()).build();
    }
}
