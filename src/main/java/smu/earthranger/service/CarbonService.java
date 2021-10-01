package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.carbon.Carbon;
import smu.earthranger.domain.Info;
import smu.earthranger.domain.carbon.Co2;
import smu.earthranger.domain.carbon.Level;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.dto.carbon.CarbonResponseInfoDto;
import smu.earthranger.repository.CarbonRepository;
import smu.earthranger.repository.InfoRepository;
import smu.earthranger.repository.MemberRepository;


import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarbonService {

    private final CarbonRepository carbonRepository;
    private final MemberRepository memberRepository;
    private final InfoRepository infoRepository;

    //탄소 발자국 계산 -> 일정 수준 넘어서면 레벨업
    @Transactional
    public Carbon saveCarbon(Long memberId,CarbonRequestDto dto){

        double distance = dto.getDistance();
        int transport = dto.getTransport();

        double result = calculateCarbon(distance, transport);
        double carCo2 = distance * Co2.carCo2.getEmission(); //자동차를 탔다면 방출할 이산화탄소 - 실제 이산화 탄소
        carCo2 = Math.round(carCo2*100)/100.0;

        double reduction = carCo2 - result;

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        member.updateTotal(reduction);
        Carbon carbon = dto.toEntity(member, result, reduction);
        Carbon savedCarbon = carbonRepository.save(carbon);

        //저장 후 유저.누적 카본 가져옴 -> level up 누적 감소량
        levelUp(member.getTotalReduction(),member);

        return savedCarbon;
    }

    public CarbonResponseInfoDto getInfo(long memberId){
        Info info = getRandomInfo();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        String treeCnt = getTreeCnt(member);//오늘 방출량에 따른 필요한 나무 수
        CarbonResponseInfoDto dto = getDto(treeCnt, info);

        return dto;
    }

    private Info getRandomInfo() {
        long count = infoRepository.count();//DB에 저장된 정보 수

        //랜덤 정보
        long randomId = (int)(Math.random()*count) +1;
        log.info("랜덤 아이디={}",randomId);

        Info info = infoRepository.findById(randomId)
                .orElseThrow(IllegalAccessError::new);
        return info;
    }

    private CarbonResponseInfoDto getDto(String treeCnt, Info info) {
        CarbonResponseInfoDto dto = CarbonResponseInfoDto.builder()
                .info(info.getContent())
                .tree(treeCnt)
                .build();
        return dto;
    }

    private String getTreeCnt(Member member) {
        double treeCnt;

        List<Carbon> carbons = carbonRepository.findByMember(member); //전체 탄소 배출량 누적
        //List<Carbon> carbons = carbonRepository.findByMemberIdAndCreatedDate(memberId, now);//하루 저장한 탄소 양

        double sum = 0;
        for (Carbon carbon : carbons) {
            sum += gToT(carbon.getEmission());
        }

        treeCnt = sum * Co2.treeNum.getEmission();
        return String.format("%.2f", treeCnt);
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
        return Math.round(result*100)/100.0;
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

    private double gToT(double gram){
        return gram * 0.000001;
    }


}
