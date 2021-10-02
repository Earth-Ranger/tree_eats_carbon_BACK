package smu.earthranger.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_from_id")
    private Member fromMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_to_id")
    private Member toMember;


    /**
     * @Builder
     * public Follow(Member fromMember, Member toMember){
     *      this.fromMember = fromMember;
     *      this.toMember = toMember;
     * }
     */
}
