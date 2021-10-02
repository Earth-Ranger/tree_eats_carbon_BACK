package smu.earthranger.dto.carbon;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smu.earthranger.domain.Member;

@Getter
@NoArgsConstructor
public class CarbonDto {
    private int treeLevel;
    private int treeCount;
    private double totalReduction;
    private double levelReduction;

    @Builder
    public CarbonDto(int treeLevel, int treeCount, double totalReduction, double levelReduction) {
        this.treeLevel = treeLevel;
        this.treeCount = treeCount;
        this.totalReduction = totalReduction;
        this.levelReduction = levelReduction;
    }

    public CarbonDto(Member member){
        this.treeLevel = member.getTreeLevel();
        this.treeCount = member.getTreeCount();
        this.totalReduction = member.getTotalReduction();
        this.levelReduction = member.getLevelReduction();
    }
}
