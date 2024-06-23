package capstone.communityservice.domain.user.repository;

import capstone.communityservice.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByOriginalId(Long originalId);

    @Query("SELECT u FROM User u WHERE u.originalId IN :userIds")
    List<User> findByOriginalIds(List<Long> userIds);
}
