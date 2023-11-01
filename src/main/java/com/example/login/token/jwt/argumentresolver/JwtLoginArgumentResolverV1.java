package com.example.login.token.jwt.argumentresolver;

import com.example.login.global.member.domain.Member;
import com.example.login.global.member.domain.MemberSession;
import com.example.login.global.member.exception.MemberNotFoundException;
import com.example.login.global.member.repository.MemberRepository;
import com.example.login.token.jwt.member.service.JwtValidateService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.example.login.token.jwt.member.basic.JwtStaticField.REFRESH_URL;

@RequiredArgsConstructor
@Slf4j
public class JwtLoginArgumentResolverV1 implements HandlerMethodArgumentResolver {

    private final MemberRepository memberRepository;
    private final JwtValidateService jwtValidateService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("JwtLoginArgumentResolver supportsParameter 실행");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(JwtLoginV1.class);
        boolean hasMemberSessionType = MemberSession.class.isAssignableFrom(parameter.getParameterType());
        return hasLoginAnnotation && hasMemberSessionType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("JwtLoginArgumentResolver resolveArgument 실행");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        Claims claims = this.getClaims(request);
        String userId = claims.getSubject();
        Member member = memberRepository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);
        return MemberSession.from(member);
    }

    private Claims getClaims(HttpServletRequest request) {
        if (!request.getRequestURI().contains(REFRESH_URL)) {
            return jwtValidateService.validateAccessToken(request);
        }
        return jwtValidateService.validateRefreshToken(request);
    }
}
