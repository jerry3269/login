package com.example.login.session.member.dto;

import com.example.login.global.member.domain.Member;

public record MemberDto(
        Long id,
        String username,
        String userId
){
    public static MemberDto of(Long id, String username, String userId) {
        return new MemberDto(id, username, userId);
    }

    public static MemberDto from(Member entity) {
        return MemberDto.of(
                entity.getId(),
                entity.getUsername(),
                entity.getUserId()
        );
    }
}
