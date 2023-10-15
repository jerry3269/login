package com.example.login.session.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

import static com.example.login.session.member.domain.constant.LoginStaticField.MEMBER_SESSION;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/filter/sign-up", "/filter/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 [{}]", requestURI);

            if (isLoginPathCheck(requestURI)) {
                log.info("인증 체크 로직 시작 [{}]", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(MEMBER_SESSION) == null) {
                    log.info("미인증 사용자 요청 [{}]", requestURI);

                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 [{}]", requestURI);
        }
    }

    private Boolean isLoginPathCheck(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
