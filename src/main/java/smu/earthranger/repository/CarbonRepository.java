package smu.earthranger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.earthranger.domain.Member;
import smu.earthranger.domain.carbon.Carbon;

import java.time.LocalDate;
import java.util.List;

public interface CarbonRepository extends JpaRepository<Carbon, Long> {

    List<Carbon> findByMemberIdAndCreatedDate(Long memberId, LocalDate now);
    List<Carbon> findByMember(Member member);
}
