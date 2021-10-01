package smu.earthranger.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smu.earthranger.domain.Follow;
import smu.earthranger.domain.Member;

@Getter
@NoArgsConstructor
public class FollowRequestDto {

    private Member fromMember;
    private Member toMember;

    @Builder
    public FollowRequestDto(Member fromMember, Member toMember){
        this.fromMember = fromMember;
        this.toMember = toMember;
    }

    public Follow toEntity(){
        return Follow.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
    }

}

