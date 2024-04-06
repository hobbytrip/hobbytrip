package capstone.communityservice.domain.server.repository;

import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.entity.ServerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServerUserRepository extends JpaRepository<ServerUser, Long> {

    @Query("select su.server from ServerUser su where su.server.id =:serverId and su.user.id =:userId")
    public Optional<Server> validateServerUser(Long serverId, Long userId);

    @Query("select su from ServerUser su join fetch su.user where su.server.id =:serverId and su.user.id =:userId")
    Optional<ServerUser> findByServerIdAndUserId(Long serverId, Long userId);

//    @Query("select s from ServerUser su join fetch su.user u join fetch su.server s where u.id =: userId")
//    public List<Server> findServerUserByUser(Long userId);
}