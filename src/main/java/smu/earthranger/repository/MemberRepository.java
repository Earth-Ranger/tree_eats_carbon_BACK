package smu.earthranger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import smu.earthranger.domain.Member;

import java.util.Optional;



public interface MemberRepository extends JpaRepository<Member, Long> {

        Optional<Member> findMemberByName(String name);

        Optional<Member> findMemberByEmail(String email);

        Page<Member> findAll(Pageable pageable);
}

