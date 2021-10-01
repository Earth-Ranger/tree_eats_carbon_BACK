package smu.earthranger.dto.follow;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smu.earthranger.domain.Member;

@Getter
@NoArgsConstructor
public class FollowMemberResponseDto {

    private Long id;
    private String name;
    private int followerCount;
    private int treeCount;

    @Builder
    private FollowMemberResponseDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.treeCount = member.getTreeCount();
        this.followerCount = member.getFollowers().size();
    }
}
