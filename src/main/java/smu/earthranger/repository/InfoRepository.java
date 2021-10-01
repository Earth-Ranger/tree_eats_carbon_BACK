package smu.earthranger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.earthranger.domain.Info;

public interface InfoRepository extends JpaRepository<Info, Long> {
}
