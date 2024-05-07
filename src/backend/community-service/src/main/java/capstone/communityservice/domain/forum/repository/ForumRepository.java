package capstone.communityservice.domain.forum.repository;

import capstone.communityservice.domain.forum.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum, Long> {
}
