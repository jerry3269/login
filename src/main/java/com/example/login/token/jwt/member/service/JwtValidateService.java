package com.example.login.token.jwt.member.service;

import com.example.login.token.jwt.member.domain.RefreshToken;
import com.example.login.token.jwt.member.dto.SecretKey;
import com.example.login.token.jwt.member.exception.ExpiredAccessTokenException;
import com.example.login.token.jwt.member.exception.ExpiredRefreshTokenException;
import com.example.login.token.jwt.member.exception.InvalidAccessTokenException;
import com.example.login.token.jwt.member.exception.InvalidRefreshTokenException;
import com.example.login.token.jwt.member.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static com.example.login.token.jwt.member.basic.JwtStaticField.BEARER;

@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public Claims validateAccessToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(BEARER.length());
            return this.validateToken(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredAccessTokenException();
        } catch (Exception e) {
            throw new InvalidAccessTokenException();
        }
    }

    public Claims validateRefreshToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authHeader.substring(BEARER.length());
            this.verifyValidRefreshToken(token);
            return this.validateToken(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException();
        } catch (Exception e) {
            throw new InvalidRefreshTokenException();
        }
    }

    private Claims validateToken(String token) {
        byte[] decodedSecretKey = secretKey.getDecoded();

        return Jwts.parserBuilder()
                .setSigningKey(decodedSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private void verifyValidRefreshToken(String refreshToken) {
        Map<String, Set<RefreshToken>> validRefreshTokens = refreshTokenRepository.getValidRefreshTokens();
        if (validRefreshTokens.values().stream().noneMatch(set -> set.contains(RefreshToken.of(refreshToken)))) {
            throw new InvalidRefreshTokenException();
        }
    }
}
