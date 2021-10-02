package smu.earthranger.dto.tree;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TreeResponseDto {
    private int treeLevel;
    private int treeCount;
    private double carbon;   //날짜 : 이산화탄소 배출 양
    private double levelReduction;

    @Builder
    public TreeResponseDto(int treeLevel, int treeCount, double carbon, double levelReduction){
        this.treeLevel = treeLevel;
        this.treeCount = treeCount;
        this.carbon = carbon;
        this.levelReduction = levelReduction;
    }

}

