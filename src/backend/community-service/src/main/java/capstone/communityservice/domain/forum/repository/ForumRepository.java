package capstone.communityservice.domain.forum.repository;

import capstone.communityservice.domain.forum.entity.Forum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Override
    @Query("select f from Forum f join fetch f.user where f.id = :forumId")
    Optional<Forum> findById(Long forumId);

    @Query("select f from Forum f join fetch f.user where f.title like CONCAT('%', :title, '%')")
    Slice<Forum> findForumsByTitle(String title, Pageable pageable);

    @Query("select f from Forum f join fetch f.user")
    Slice<Forum> findForums(Pageable pageable);
}
