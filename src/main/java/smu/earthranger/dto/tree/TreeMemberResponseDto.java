package smu.earthranger.dto.tree;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//클릭 시 나오는 이웃 다이얼로그
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TreeMemberResponseDto {

    private String name;
    private int treeLevel;
    private int treeCount;
    private int followingCount;
    private int followerCount;


}
