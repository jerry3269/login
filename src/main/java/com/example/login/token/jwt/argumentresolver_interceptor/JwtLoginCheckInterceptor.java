package com.example.login.token.jwt.argumentresolver_interceptor;

import com.example.login.global.member.domain.Member;
import com.example.login.global.member.domain.MemberSession;
import com.example.login.global.member.exception.MemberNotFoundException;
import com.example.login.global.member.repository.MemberRepository;
import com.example.login.token.jwt.member.service.JwtValidateService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.login.token.jwt.member.basic.JwtStaticField.REFRESH_URL;

@Slf4j
@RequiredArgsConstructor
public class JwtLoginCheckInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;
    private final JwtValidateService jwtValidateService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String requestURI = request.getRequestURI();

        log.info("MEMBER 로그인 인증 인터 셉터 실행 [{}]", requestURI);
        Claims claims = this.getClaims(request);
        String userId = claims.getSubject();
        Member member = memberRepository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);
        request.setAttribute("MemberSession", MemberSession.from(member));
        log.info("MEMBER 로그인 확인 성공");
        return true;
    }

    private Claims getClaims(HttpServletRequest request) {
        if (!request.getRequestURI().contains(REFRESH_URL)) {
            return jwtValidateService.validateAccessToken(request);
        }
        return jwtValidateService.validateRefreshToken(request);
    }
}
