package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Follow;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.user.MemberRankingResponseDto;
import smu.earthranger.repository.FollowRepository;
import smu.earthranger.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public Page<MemberRankingResponseDto> getByPermission(Long memberId, String user, Pageable pageable){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        if(user.equals("follow")){
            return getFollowMember(pageable,member);
        }

        return getAllMember(pageable);

    }

    private Page<MemberRankingResponseDto> getAllMember(Pageable pageable) {
        Page<Member> sorted = memberRepository.findAll(pageable);

        return sorted
                .map(MemberRankingResponseDto::new);
    }

    private Page<MemberRankingResponseDto> getFollowMember(Pageable pageable, Member member) {
        List<Follow> sorted = followRepository.findFollowByFromMember(member);
        List<Member> members = new ArrayList<>();
        for (Follow follow : sorted) {
            members.add(follow.getToMember());
        }

        //list -> page
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), members.size());
        final Page<Member> page = new PageImpl<>(members.subList(start, end), pageable, members.size());

        return page
                .map(MemberRankingResponseDto::new);
    }

}



