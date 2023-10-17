package com.example.login.session.member.service;


import com.example.login.global.member.domain.Member;
import com.example.login.global.member.domain.MemberSession;
import com.example.login.global.member.dto.LoginForm;
import com.example.login.global.member.dto.SignUpForm;
import com.example.login.global.member.exception.MemberNotFoundException;
import com.example.login.global.member.exception.MemberPasswordNotMatchException;
import com.example.login.global.member.repository.MemberRepository;
import com.example.login.session.member.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.login.global.member.domain.MemberSession.MEMBER_SESSION;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginService {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberDto signUp(SignUpForm signUpForm) {
        return memberService.save(signUpForm);
    }

    public MemberDto login(LoginForm loginForm, HttpServletRequest request) {
        Member member = memberRepository.findByUserId(loginForm.userId()).orElseThrow(MemberNotFoundException::new);
        if (passwordEncoder.matches(loginForm.password(), member.getPassword())) {
            MemberSession memberSession = MemberSession.from(member);
            HttpSession session = request.getSession();
            session.setAttribute(MEMBER_SESSION, memberSession);
            return MemberDto.from(member);
        }
        throw new MemberPasswordNotMatchException();
    }

    public HttpSession logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return request.getSession(false);
    }
}
