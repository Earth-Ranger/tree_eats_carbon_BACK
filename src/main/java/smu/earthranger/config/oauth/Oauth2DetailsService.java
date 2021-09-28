package smu.earthranger.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import smu.earthranger.config.auth.MemberDetails;
import smu.earthranger.domain.Member;
import smu.earthranger.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Map;
import java.util.UUID;
/**
@RequiredArgsConstructor
@Service
public class Oauth2DetailsService extends DefaultOAuth2UserService{

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> user_map = oAuth2User.getAttributes();
        String email = (String) user_map.get("email");
        String name = (String) user_map.get("name");
        String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString());

        Member check_user = memberRepository.findMemberByEmail(email);
        if (check_user == null) {    //최초 로그인
           Member member = Member.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build();
            return new MemberDetails(memberRepository.save(member), user_map);
        } else {      //이미 회원가입 되어있음
            return new MemberDetails(check_user);
        }
    }
}
**/
