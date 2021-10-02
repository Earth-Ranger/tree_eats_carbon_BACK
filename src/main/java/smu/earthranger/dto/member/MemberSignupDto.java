package smu.earthranger.dto.member;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Builder
@Data
@AllArgsConstructor
public class MemberSignupDto {

    @Size(min = 1, max=50, message = "이메일은 2 ~ 50자여야 합니다.")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    @Size(min = 1, max = 30, message= "이름은 1 ~ 30자여야 합니다.")
    @NotBlank(message = "이름은 필수 항목입니다")
    private String name;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    public MemberSignupDto() {
    }
}
