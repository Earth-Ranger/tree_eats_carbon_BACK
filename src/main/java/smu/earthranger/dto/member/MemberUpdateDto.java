package smu.earthranger.dto.member;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberUpdateDto {
    @Size(min = 1, max = 30, message= "이름은 1 ~ 30자여야 합니다.")
    @NotBlank(message = "이름은 필수 항목입니다")
    private String name;

    public MemberUpdateDto(String name) {
        this.name = name;
    }
}
