package smu.earthranger.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import smu.earthranger.domain.Member;

@Getter
@NoArgsConstructor
@ToString
public class MemberRankingResponseDto {
    private Long memberId;
    private int treeCount;
    private String name;

    public MemberRankingResponseDto(Member member) {
        this.memberId = member.getId();
        this.treeCount = member.getTreeCount();
        this.name = member.getName();
    }
}
