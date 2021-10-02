package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.carbon.Carbon;
import smu.earthranger.domain.carbon.Co2;
import smu.earthranger.domain.carbon.Level;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.carbon.CarbonDto;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.repository.CarbonRepository;
import smu.earthranger.repository.MemberRepository;



@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarbonService {

    private final CarbonRepository carbonRepository;
    private final MemberRepository memberRepository;

    //탄소 발자국 계산 -> 일정 수준 넘어서면 레벨업
    @Transactional
    public CarbonDto saveCarbon(Long memberId,CarbonRequestDto dto){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        double distance = dto.getDistance();
        int transport = dto.getTransport();
        double result = calculateCarbon(distance, transport); //이산화탄소 방출량
        double carCo2 = distance * Co2.carCo2.getEmission(); //자동차를 탔다면 방출할 이산화탄소
        carCo2 = getDouble(carCo2);

        double reduction = carCo2 - result; //저감량
        reduction = getDouble(reduction);

        member.updateTotal(reduction);

        Carbon carbon = dto.toEntity(member, result, reduction);
        carbonRepository.save(carbon);

        //저장 후 유저.누적 카본 가져옴 -> level up 누적 감소량
        levelUp(member.getLevelReduction(),member);

        return new CarbonDto(member);
    }

    private double getDouble(double reduction) {
        return Math.round(reduction * 100) / 100.0;
    }


    private double calculateCarbon(double distance, int transport) {
        double result = 0.0;
        //도보 or 자전거 -> 0
        if(transport == 0){
            result = 0;
        }
        //지하철
        else if(transport == 1) {
            result = distance * Co2.subwayCo2.getEmission();
        }
        //버스
        else if(transport == 2){
            result = distance * Co2.busCo2.getEmission();
        }
        return getDouble(result);
    }

    private void levelUp(double reduction, Member member){
        if (reduction >= Level.Lv5.level())
            member.updateTreeCnt(1);
        else if (reduction >= Level.Lv4.level())
            member.updateTree(5);
        else if (reduction >= Level.Lv3.level())
            member.updateTree(4);
        else if (reduction >= Level.Lv2.level())
            member.updateTree(3);
        else if (reduction >= Level.Lv1.level())
            member.updateTree(2);
    }


}
