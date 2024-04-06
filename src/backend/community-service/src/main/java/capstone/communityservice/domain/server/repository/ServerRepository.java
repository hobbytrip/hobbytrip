package capstone.communityservice.domain.server.repository;

import capstone.communityservice.domain.server.entity.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServerRepository extends JpaRepository<Server, Long> {

    @Query(value = "select distinct(s.*) " +
            "from server s " +
            "join server_user su on s.server_id = su.server_id " +
            "where s.open = true " +
            "group by s.server_id " +
            "order by count(su.server_id) desc limit 9",
            nativeQuery = true)
    List<Server> findTopOpenServer();

    @Query("select distinct(s) from Server s join fetch s.serverUsers where s.name like CONCAT('%', :name, '%')")
    Page<Server> findServerWithPaging(String name, Pageable pageable);

}
