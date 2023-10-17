package com.example.login.token.jwt.argumentresolver;

import com.example.login.global.member.repository.MemberRepository;
import com.example.login.token.jwt.member.service.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class JwtArgumentResolverWebConfig implements WebMvcConfigurer {

    private final MemberRepository memberRepository;
    private final JwtValidateService jwtValidateService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtLoginArgumentResolver(memberRepository, jwtValidateService));
    }
}
