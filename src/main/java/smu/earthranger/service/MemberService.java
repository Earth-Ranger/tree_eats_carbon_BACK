package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.member.MemberLoginDto;
import smu.earthranger.dto.member.MemberSignupDto;
import smu.earthranger.dto.member.MemberUpdateDto;
import smu.earthranger.dto.user.TokenDto;
import smu.earthranger.jwt.JwtTokenProvider;
import smu.earthranger.repository.MemberRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public Long save(MemberSignupDto memberSignupDto) {

        // 중복 회원 검증
        if(memberRepository.findMemberByEmail(memberSignupDto.getEmail()).isPresent())
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        return memberRepository.save(Member.builder()
                .email(memberSignupDto.getEmail())
                .password(passwordEncoder.encode(memberSignupDto.getPassword()))
                .name(memberSignupDto.getName())
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    public Member findMemberById(Long userId){
        return memberRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 회원입니다."));
    }
    //email, password
    public TokenDto getToken(MemberLoginDto memberLoginDto){
        Member member = memberRepository.findMemberByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("가입되지 않은 이메일 입니다."));

        if(!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())){
            throw new IllegalStateException("잘못된 비밀번호 입니다.");
        }

        String token = jwtTokenProvider.createToken(member.getEmail(), member.getRoles());

        return new TokenDto(token);
    }

    //중복검사
    public Map<String, Boolean> checkName(String name) {//닉네임 중복 검사
        Map<String, Boolean> returnJson = new HashMap<>();
        Member member = memberRepository.findMemberByName(name)
                .orElse(null);

        if(member == null){ //중복회원 없으면
            returnJson.put("message", true);
        }
        else {
            returnJson.put("message", false);
        }
        return returnJson;
    }


}