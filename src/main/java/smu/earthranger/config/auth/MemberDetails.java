package smu.earthranger.config.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import smu.earthranger.domain.Member;

import java.util.Collection;
import java.util.Map;

@Data
public class MemberDetails implements UserDetails{

    private Member member;
    private Map<String, Object> attributes;

    public MemberDetails(Member member) {
        this.member = member;
    }

    public MemberDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
    }

    public void updateMember(Member member) { this.member = member; }

    /**
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    // 해당 유저의 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 사용자 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
    **/
}
