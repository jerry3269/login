package com.example.login.session.member.service;

import com.example.login.global.member.domain.Member;
import com.example.login.global.member.domain.MemberSession;
import com.example.login.global.member.dto.SignUpForm;
import com.example.login.global.member.exception.MemberDuplicationException;
import com.example.login.global.member.exception.MemberNotFoundException;
import com.example.login.global.member.repository.MemberRepository;
import com.example.login.session.member.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.login.global.member.domain.MemberSession.MEMBER_SESSION;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberDto save(SignUpForm signUpForm) {
        validDupUserId(signUpForm.userId());
        return MemberDto.from(memberRepository.save(signUpForm.toEntity(passwordEncoder)));
    }

    private void validDupUserId(String userId) {
        Optional<Member> optionalUser = memberRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            throw new MemberDuplicationException();
        }
    }

    public MemberDto getBySession(MemberSession session) {
        return memberRepository.findByUserId(session.userId())
                .map(MemberDto::from)
                .orElseThrow(MemberNotFoundException::new);
    }

    public MemberDto getByRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        MemberSession memberSession = (MemberSession) session.getAttribute(MEMBER_SESSION);
        return memberRepository.findByUserId(memberSession.userId())
                .map(MemberDto::from)
                .orElseThrow(MemberNotFoundException::new);
    }
    @Transactional
    public void delete(MemberSession memberSession) {
        memberRepository.deleteById(memberSession.id());
    }
}
