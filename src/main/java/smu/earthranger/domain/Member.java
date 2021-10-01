package smu.earthranger.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import smu.earthranger.dto.carbon.Carbon;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    private int treeLevel;

    private int treeCount;

    private double totalReduction;

    @OneToMany(mappedBy = "member")
    private List<Carbon> carbon = new ArrayList<>();

    @OneToMany(mappedBy = "fromMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "toMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();   //member_roles table 생성되면서 오류남

    public void update(String email, String name,String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void updateTree(int treeLevel){
        this.treeLevel = treeLevel;
    }

    public void updateTreeCnt(int treeLevel){
        this.treeLevel = treeLevel;
        this.treeCount += 1;
    }

    public void updateTotal(double reduction){
        this.totalReduction += reduction;
    }

    @Builder
    private Member(String email, String name, String password, List<String> roles,  double totalReduction) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.totalReduction = totalReduction;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}