package com.example.login.token.jwt.member.repository;

import com.example.login.token.jwt.member.domain.RefreshToken;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.*;

@Getter
@Repository
public class RefreshTokenRepository {

    Map<String, Set<RefreshToken>> validRefreshTokens = new HashMap<>();

    public String save(String userId, String refreshToken) {
        Set<RefreshToken> refreshTokens = validRefreshTokens.getOrDefault(userId, new HashSet<>());
        refreshTokens.add(RefreshToken.of(refreshToken));
        validRefreshTokens.put(userId, refreshTokens);
        return refreshToken;
    }

    public void invalidate(String userId) {
        validRefreshTokens.remove(userId);
    }

    public void clear() {
        validRefreshTokens.clear();
    }



}
