package capstone.communityservice.domain.dm.repository;

import capstone.communityservice.domain.dm.entity.Dm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DmRepository extends JpaRepository<Dm, Long> {
}
