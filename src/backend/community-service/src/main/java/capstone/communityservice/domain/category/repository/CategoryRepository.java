package capstone.communityservice.domain.category.repository;

import capstone.communityservice.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.server.id = :serverId")
    List<Category> findByServerId(Long serverId);

    @Modifying
    @Query("update Category c set c.deleted = true where c.server.id = :serverId")
    void deleteAllByServerid(Long serverId);
}
