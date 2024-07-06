package capstone.communityservice.domain.dm.repository;

import capstone.communityservice.domain.dm.entity.Dm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DmRepository extends JpaRepository<Dm, Long> {

    @Query("select distinct(d) from Dm d join fetch d.dmUsers du where du.user.id = :userId")
    List<Dm> findDmsWithUserId(Long userId);

    @Query("select distinct(d) from Dm d join fetch d.dmUsers du join fetch du.user where d.id = :dmId")
    Optional<Dm> findDmWithUserById(Long dmId);
}
