package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Info;
import smu.earthranger.domain.Member;
import smu.earthranger.domain.carbon.Carbon;
import smu.earthranger.domain.carbon.Co2;
import smu.earthranger.dto.carbon.CarbonResponseInfoDto;
import smu.earthranger.repository.CarbonRepository;
import smu.earthranger.repository.InfoRepository;
import smu.earthranger.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InfoService {

    private final InfoRepository infoRepository;
    private final MemberRepository memberRepository;
    private final CarbonRepository carbonRepository;

    public CarbonResponseInfoDto getInfo(long memberId){
        Info info = getRandomInfo();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        String treeCnt = getTreeCnt(member);//방출량에 따른 필요한 나무 수
        CarbonResponseInfoDto dto = getDto(treeCnt, info);

        return dto;
    }

    private Info getRandomInfo() {
        long count = infoRepository.count();//DB에 저장된 정보 수

        //랜덤 정보
        long randomId = (int)(Math.random()*count) +1;

        Info info = infoRepository.findById(randomId)
                .orElseThrow(IllegalAccessError::new);
        return info;
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

    private CarbonResponseInfoDto getDto(String treeCnt, Info info) {
        CarbonResponseInfoDto dto = CarbonResponseInfoDto.builder()
                .info(info.getContent())
                .tree(treeCnt)
                .build();
        return dto;
    }

    private double gToT(double gram){
        return gram * 0.000001;
    }
}
