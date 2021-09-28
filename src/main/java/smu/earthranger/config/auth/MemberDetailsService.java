package smu.earthranger.config.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import smu.earthranger.domain.Member;
import smu.earthranger.repository.MemberRepository;

@RequiredArgsConstructor
@Service
// UserDetailsService: DB에서 유저 정보를 직접 가져오는 인터페이스
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    // Spring Security 에서 사용자 정보를 찾아서 로그인
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(username);

        if(member == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return new MemberDetails(member);
        }
    }
}

