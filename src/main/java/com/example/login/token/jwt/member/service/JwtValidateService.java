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

@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final SecretKey secretKey;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public Claims validateToken(HttpServletRequest request) {
        String accessToken = request.getHeader("AccessToken");
        byte[] decodedSecretKey = secretKey.getDecoded();

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedSecretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            Claims claims = validateRefreshToken(request);
            throw new ExpiredAccessTokenException("Acess토큰 만료! 새로 발급된 AccessToken: " +
                    jwtProvider.createAccessToken(claims.getSubject()));
        } catch (Exception e) {
            throw new InvalidAccessTokenException();
        }
    }

    private Claims validateRefreshToken(HttpServletRequest request) {
        String refreshToken = getRefreshTokenFromRequest(request);
        verifyValidRefreshToken(refreshToken);
        byte[] decodedSecretKey = secretKey.getDecoded();

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(decodedSecretKey)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ExpiredRefreshTokenException();
        } catch (Exception e) {
            throw new InvalidRefreshTokenException();
        }
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(HttpHeaders.SET_COOKIE))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(InvalidRefreshTokenException::new);
    }

    private void verifyValidRefreshToken(String refreshToken) {
        Map<String, Set<RefreshToken>> validRefreshTokens = refreshTokenRepository.getValidRefreshTokens();
        if (validRefreshTokens.values().stream().noneMatch(set -> set.contains(RefreshToken.of(refreshToken)))) {
            throw new InvalidRefreshTokenException();
        }
    }
}
