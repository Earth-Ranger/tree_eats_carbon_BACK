package smu.earthranger.dto.carbon;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smu.earthranger.domain.carbon.Carbon;
import smu.earthranger.domain.Member;

@Getter
@NoArgsConstructor
public class CarbonRequestDto {

    private double distance;
    private int transport;

    @Builder
    public CarbonRequestDto(double distance, int transport) {
        this.distance = distance;
        this.transport = transport;
    }

    public Carbon toEntity(Member member, double emission, double reduction){
        return Carbon.builder()
                .distance(distance)
                .emission(emission)
                .reduction(reduction)
                .member(member)
                .build();
    }

}
