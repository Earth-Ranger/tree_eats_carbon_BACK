package smu.earthranger.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@Data
public class MemberUpdateDto {
    @Size(min = 1, max = 30, message= "이름은 1 ~ 30자여야 합니다.")
    @NotBlank(message = "이름은 필수 항목입니다")
    private String name;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;
}
