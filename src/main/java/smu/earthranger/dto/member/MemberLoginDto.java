package smu.earthranger.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginDto {

    private String email;
    private String password;

    public MemberLoginDto(String email, String password){
        this.email = email;
        this.password = password;
    }
}

