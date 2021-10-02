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
import smu.earthranger.domain.Info;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.dto.carbon.CarbonResponseInfoDto;
import smu.earthranger.dto.user.MemberRankingResponseDto;
import smu.earthranger.repository.InfoRepository;
import smu.earthranger.repository.MemberRepository;

import java.util.List;

@SpringBootTest
class CarbonServiceTest {

    @Autowired CarbonService carbonService;
    @Autowired InfoRepository infoRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired RankService rankService;
    @Autowired InfoService infoService;

    @Test
    public void saveCarbon() throws Exception{
        //given
        
        //when
        //then
    }

    //카본 -> 오늘의 방출량,
    @Test
    @Rollback(value = false)
    public void getInfo() throws Exception{


        CarbonRequestDto carbon1 = getCarbon(1, 234.3);
        CarbonRequestDto carbon2 = getCarbon(2, 244.3);
        CarbonRequestDto carbon3 = getCarbon(2, 244.3);


        Member member = getMember("te2122312t","te2@test.com","dkdkkdk23",0);
        Member member2 = getMember("te2212222t2","td@test.com","ff2",0);
        Member save = memberRepository.save(member);
        memberRepository.save(member2);

        carbonService.saveCarbon(save.getId(),carbon1);
        carbonService.saveCarbon(save.getId(),carbon2);
        carbonService.saveCarbon(save.getId(),carbon3);

        //when
        CarbonResponseInfoDto info = infoService.getInfo(save.getId());

        //then
        System.out.println(info);

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