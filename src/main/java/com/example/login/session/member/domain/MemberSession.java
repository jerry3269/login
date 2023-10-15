package com.example.login.session.member.domain;

import lombok.Builder;

public record MemberSession(
        Long id,
        String username,
        String userId,
        String password
        ){

    @Builder
    public static MemberSession of(Long id, String username, String userId, String password) {
        return new MemberSession(id, username, userId, password);
    }

    public static MemberSession from(Member member) {
        return MemberSession.builder()
                .id(member.getId())
                .username(member.getUsername())
                .userId(member.getUserId())
                .password(member.getPassword()).build();
    }


}
