package capstone.communityservice.domain.forum.repository;

import capstone.communityservice.domain.forum.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
