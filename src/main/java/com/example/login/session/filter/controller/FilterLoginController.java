package com.example.login.session.filter.controller;

import com.example.login.global.member.dto.LoginForm;
import com.example.login.global.member.dto.SignUpForm;
import com.example.login.session.member.dto.MemberResponse;
import com.example.login.session.member.service.LoginService;
import com.example.login.session.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/filter")
@RestController
public class FilterLoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/get")
    public MemberResponse get(HttpServletRequest request) {
        return MemberResponse.from(memberService.getByRequest(request));
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
    public String logout(HttpServletRequest request) {
        loginService.logout(request);
        return "success";
    }
}
