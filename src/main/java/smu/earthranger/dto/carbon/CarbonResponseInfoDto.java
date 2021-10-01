package smu.earthranger.dto.carbon;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CarbonResponseInfoDto {

    private String tree;
    private String info;

    @Builder
    public CarbonResponseInfoDto(String tree, String info) {
        this.tree = tree;
        this.info = info;
    }
}