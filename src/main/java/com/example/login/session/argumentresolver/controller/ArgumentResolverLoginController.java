package com.example.login.session.argumentresolver.controller;

import com.example.login.session.argumentresolver.Login;
import com.example.login.session.member.domain.MemberSession;
import com.example.login.session.member.dto.LoginForm;
import com.example.login.session.member.dto.SignUpForm;
import com.example.login.session.member.dto.MemberResponse;
import com.example.login.session.member.service.LoginService;
import com.example.login.session.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/argument-resolver")
@RestController
public class ArgumentResolverLoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/get")
    public MemberResponse get(@Login MemberSession memberSession) {
        return MemberResponse.from(memberService.getBySession(memberSession));
    }

    @PostMapping("/sign-up")
    public MemberResponse signUp(@RequestBody @Valid SignUpForm signUpForm) {
        return MemberResponse.from(loginService.signUp(signUpForm));
    }

    @PostMapping("/login")
    public MemberResponse login(
            @RequestBody @Valid LoginForm loginForm,
            HttpServletRequest request
            ) {
        MemberResponse memberResponse = MemberResponse.from(loginService.login(loginForm, request));
        return memberResponse;
    }

    @GetMapping("/logout")
    public String logout(@Login MemberSession memberSession, HttpServletRequest request) {
        loginService.logout(request);
        return "success";
    }
}
