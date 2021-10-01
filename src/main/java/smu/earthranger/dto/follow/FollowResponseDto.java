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
    private int followingCount;
    private int treeCount;
    private int treeLevel;

    @Builder
    public FollowResponseDto(Follow follow){
        Member toMem = follow.getToMember();

        this.id = toMem.getId();
        this.name = toMem.getName();
        this.followerCount = toMem.getFollowers().size();
        this.followingCount = toMem.getFollowings().size();
        this.treeCount = toMem.getTreeCount();
        this.treeLevel = toMem.getTreeLevel();
    }

}

