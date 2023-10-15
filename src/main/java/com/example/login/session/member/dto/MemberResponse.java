package com.example.login.session.member.dto;

public record MemberResponse(
        String username,
        String userId
){

    public static MemberResponse of(String username, String userId) {
        return new MemberResponse(username, userId);
    }

    public static MemberResponse from(MemberDto dto) {
        return MemberResponse.of(
                dto.username(),
                dto.userId()
        );
    }
}
