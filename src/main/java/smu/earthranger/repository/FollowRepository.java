package smu.earthranger.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Follow;
import smu.earthranger.domain.Member;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromMember_IdAndToMember_Name(Long id, String name);

    Optional<Follow> findByFromMember_IdAndToMember_Email(Long id, String email);

    List<Follow> findByFromMember(Member member);

    List<Follow> findFollowByFromMember(Member member);

    @Transactional
    void deleteByFromMemberAndToMember(Member fromMember, Member toMember);
}

