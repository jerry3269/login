package com.example.login.token.jwt.argumentresolver_interceptor;

import com.example.login.global.member.repository.MemberRepository;
import com.example.login.token.jwt.member.service.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class JwtWebConfigV2 implements WebMvcConfigurer {

    private final MemberRepository memberRepository;
    private final JwtValidateService jwtValidateService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtLoginCheckInterceptor(memberRepository, jwtValidateService))
                .order(1)
                .addPathPatterns("/jwt/v2/**")
                .excludePathPatterns("/jwt/v2/sign-up", "/jwt/v2/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JwtLoginArgumentResolverV2());
    }
}
