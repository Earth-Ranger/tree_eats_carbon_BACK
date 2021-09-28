package smu.earthranger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.earthranger.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
        Member findMemberByEmail(String email);
}
