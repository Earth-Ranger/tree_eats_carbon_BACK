package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.domain.carbon.Carbon;
import smu.earthranger.dto.tree.TreeMemberResponseDto;
import smu.earthranger.dto.tree.TreeResponseDto;
import smu.earthranger.repository.CarbonRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final MemberService memberService;
    private final CarbonRepository carbonRepository;

    //본인 식물 조회
    @Transactional
    public TreeResponseDto findSelfTree(Long userId){
        Member member = memberService.findMemberById(userId);
        List<Carbon> todayCarbonList = carbonRepository.findByMemberIdAndCreatedDate(userId, LocalDate.now());

        return TreeResponseDto.builder()
                .treeCount(member.getTreeCount())
                .treeLevel(member.getTreeLevel())
                .levelReduction(member.getLevelReduction())
                .carbon(extractOneDayCo2(todayCarbonList)).build();
    }

    public double extractOneDayCo2(List<Carbon> carbonList){

        double totalReduction = 0.0;

        for(Carbon carbon : carbonList){
            totalReduction += carbon.getReduction();
        }
        return totalReduction;
    }

    //이웃 식물 조회
    @Transactional
    public TreeMemberResponseDto findMemberTree(Long userId){
        Member member = memberService.findMemberById(userId);

        return TreeMemberResponseDto.builder()
                .name(member.getName())
                .treeCount(member.getTreeCount())
                .treeLevel(member.getTreeLevel())
                .followerCount(member.getFollowers().size())
                .followingCount(member.getFollowings().size()).build();
    }
}
