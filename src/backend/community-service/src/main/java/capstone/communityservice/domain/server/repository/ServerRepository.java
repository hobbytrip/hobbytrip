package capstone.communityservice.domain.server.repository;

import capstone.communityservice.domain.server.dto.response.OpenServerQuery;
import capstone.communityservice.domain.server.entity.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {

    @Query(value = "select s.server_id as id, s.name, s.profile, s.description, " +
            "s.manager_id as managerId, s.open, count(su.server_id) as userCount " +
            "from server s " +
            "join server_user su on s.server_id = su.server_id " +
            "where s.open = true and su.deleted = false " +
            "group by s.server_id " +
            "order by count(su.server_id) desc limit 9",
            nativeQuery = true)
    List<OpenServerQuery> findTopOpenServer();


    @Query("select s from Server s where s.name like CONCAT('%', :name, '%') and s.open = true")
    Page<Server> findServerWithPaging(String name, Pageable pageable);

    @Query("select distinct(s) from Server s join fetch s.serverUsers su where su.user.id = :userId and s.deleted = false")
    List<Server> findServersWithUserId(Long userId);

    @Query("select distinct(s) from Server s join fetch s.serverUsers su join fetch su.user where s.id = :serverId")
    Optional<Server> findServerWithUserById(Long serverId);
}
