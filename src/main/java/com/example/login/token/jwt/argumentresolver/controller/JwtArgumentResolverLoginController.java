package com.example.login.token.jwt.argumentresolver.controller;

import com.example.login.global.member.domain.MemberSession;
import com.example.login.global.member.dto.LoginForm;
import com.example.login.global.member.dto.SignUpForm;
import com.example.login.token.jwt.argumentresolver.JwtLogin;
import com.example.login.token.jwt.member.dto.MemberWithTokenResponse;
import com.example.login.token.jwt.member.service.JwtLoginService;
import com.example.login.token.jwt.member.service.JwtMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/jwt/argument-resolver")
@RestController
public class JwtArgumentResolverLoginController {

    private final JwtLoginService jwtLoginService;
    private final JwtMemberService memberService;

    @GetMapping("/get")
    public MemberWithTokenResponse get(@JwtLogin MemberSession memberSession) {
        return MemberWithTokenResponse.from(memberService.getBySession(memberSession));
    }

    @PostMapping("/sign-up")
    public MemberWithTokenResponse signUp(@RequestBody @Valid SignUpForm signUpForm) {
        return MemberWithTokenResponse.withoutToken(jwtLoginService.signUp(signUpForm));
    }

    @PostMapping("/login")
    public MemberWithTokenResponse login(
            @RequestBody @Valid LoginForm loginForm,
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        MemberWithTokenResponse memberWithTokenResponse = MemberWithTokenResponse.from(jwtLoginService.login(loginForm, request, response));
        return memberWithTokenResponse;
    }

    @GetMapping("/logout")
    public String logout(@JwtLogin MemberSession memberSession, HttpServletRequest request) {
        jwtLoginService.logout(memberSession);
        return "success";
    }
}
