package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import smu.earthranger.config.auth.MemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.member.MemberSignupDto;
import smu.earthranger.dto.member.MemberUpdateDto;
import smu.earthranger.handler.CustomValidationException;
import smu.earthranger.repository.MemberRepository;

@Service
@RequiredArgsConstructor

public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Member save(MemberSignupDto memberSignupDto) {

        // 중복 회원 검증
        if(memberRepository.findMemberByEmail(memberSignupDto.getEmail()) != null)
            throw new CustomValidationException("이미 존재하는 회원입니다.");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return memberRepository.save(Member.builder()
                .email(memberSignupDto.getEmail())
                .name(memberSignupDto.getName())
                .password(encoder.encode(memberSignupDto.getPassword()))
                .build());
    }

    // 회원 정보 수정
    @Transactional
    public void update(MemberUpdateDto memberUpdateDto, MemberDetails memberDetails){
        Member member = memberRepository.findById(memberDetails.getMember().getId()).orElseThrow(() -> { return new CustomValidationException("없는 회원입니다.");});
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        member.update(
                encoder.encode(memberUpdateDto.getPassword()),
                memberUpdateDto.getName(),
                memberUpdateDto.getPassword()
        );

        //정보 변경
        memberDetails.updateMember(member);
    }
}
