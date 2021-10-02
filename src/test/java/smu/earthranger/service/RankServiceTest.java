package smu.earthranger.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.dto.user.MemberRankingResponseDto;
import smu.earthranger.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RankServiceTest {

    @Autowired RankService rankService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CarbonService carbonService;
    @Autowired
    FollowService followService;

    @Test
    public void showRank() throws Exception{
        //given
        CarbonRequestDto carbon1 = getCarbon(1, 123.2);
        CarbonRequestDto carbon2 = getCarbon(1, 233.2);
        CarbonRequestDto carbon3 = getCarbon(2, 34444.1);

//        Member member = getMember("tg1","jm11@test.com","dkdkkdk23",0);
//        Member member2 = getMember("df1e","dum1@test.com","ff2",0);
//        Member member3 = getMember("zz1z","se1@test.com","fdfdff2",0);
//        Member save = memberRepository.save(member);
//        Member save1 = memberRepository.save(member2);
//        Member save2 = memberRepository.save(member3);

        carbonService.saveCarbon(2L,carbon1);
        carbonService.saveCarbon(2L,carbon2);
        carbonService.saveCarbon(2L,carbon3);

        Pageable pageable = PageRequest.of(0, 20, Sort.by( Sort.Direction.DESC,"totalReduction"));
//        followService.followMember(member.getId(),member2.getName()); //member -> member2 팔로우
//
//        Page<MemberRankingResponseDto> follow = rankService.getByPermission(member.getId(), "follow", pageable);

//        for (MemberRankingResponseDto following : follow) {
//            System.out.println(following);
//        }

    }

    private CarbonRequestDto getCarbon(int i2, double v) {
        return CarbonRequestDto.builder()
                .transport(i2)
                .distance(v)
                .build();
    }

    private Member getMember(String name, String email, String password, double reduction) {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .totalReduction(reduction)
                .build();
        return member;
    }

    @Test
    @Rollback(value = false)
    public void getRank(){

        //given
        CarbonRequestDto carbon1 = getCarbon(1, 234.3);
        CarbonRequestDto carbon2 = getCarbon(2, 244.3);
        CarbonRequestDto carbon3 = getCarbon(2, 244.3);

        Member member = getMember("test","test@test.com","dkdkkdk23",0);
        Member member2 = getMember("test2","gg@test.com","ff2",0);
        Member member3 = getMember("test3","ggg@test.com","fdfdff2",0);
        Member save = memberRepository.save(member);
        Member save1 = memberRepository.save(member2);
        Member save2 = memberRepository.save(member3);

        carbonService.saveCarbon(save.getId(),carbon1);
        carbonService.saveCarbon(save1.getId(),carbon2);
        carbonService.saveCarbon(save1.getId(),carbon3);

        Pageable pageable = PageRequest.of(0, 20, Sort.by( Sort.Direction.DESC,"totalReduction"));
        //when
        Page<MemberRankingResponseDto> all = rankService.getByPermission(save.getId(), "all", pageable);

        //then
        //Assertions.assertThat(byPermission.get(0).getMemberId()).isEqualTo(member.getId());

        for (MemberRankingResponseDto userRankingResponseDto : all) {
            System.out.println(userRankingResponseDto);
        }

    }

}