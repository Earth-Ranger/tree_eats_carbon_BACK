package smu.earthranger.domain.carbon;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smu.earthranger.domain.BaseTimeEntity;
import smu.earthranger.domain.Member;

import javax.persistence.*;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Carbon extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "carbon_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private double emission;
    private double reduction;
    private double distance;

    @Builder
    public Carbon(Member member, double emission, double reduction, double distance) {
        this.member = member;
        this.emission = emission;
        this.reduction = reduction;
        this.distance = distance;
    }

    @Builder
    public Carbon( double distance) {
        this.distance = distance;
    }
}
