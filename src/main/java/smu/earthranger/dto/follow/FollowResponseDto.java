package smu.earthranger.dto.follow;

import lombok.*;
import smu.earthranger.domain.Follow;
import smu.earthranger.domain.Member;

@Getter
@NoArgsConstructor
public class FollowResponseDto {

    private Long id;
    private String name;
    private int followerCount;
    private int treeCount;

    @Builder
    public FollowResponseDto(Follow follow){
        Member toMem = follow.getToMember();

        this.id = toMem.getId();
        this.name = toMem.getName();
        this.followerCount = toMem.getFollowers().size();
        this.treeCount = toMem.getTreeCount();
    }

    @Builder
    public FollowResponseDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.treeCount = member.getTreeCount();
        this.followerCount = member.getFollowers().size();
    }

}
