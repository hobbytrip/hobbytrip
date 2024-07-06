package capstone.communityservice.domain.dm.repository;

import capstone.communityservice.domain.dm.entity.DmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DmUserRepository extends JpaRepository<DmUser, Long> {

    @Query("select du from DmUser du where du.dm.id =:dmId and du.user.id =:userId")
    Optional<DmUser> findByDmIdAndUserId(Long dmId, Long userId);

    @Query(value = "select du.user_id " +
            "from dm_user du " +
            "where du.dm_id = :dmId ",
            nativeQuery = true)
    List<Long> findUserIdsByDmId(Long dmId);

    @Query(value = "select du.dm_id " +
            "from dm_user du " +
            "where du.user_id = :userId ",
            nativeQuery = true)
    List<Long> findDmIdsByUserId(Long userId);

    @Modifying
    @Query("update DmUser du set du.deleted = true where du.dm.id = :dmId")
    void deleteAllByDmId(Long dmId);
}
