package capstone.communityservice.domain.forum.repository;

import capstone.communityservice.domain.forum.entity.Forum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ForumRepository extends JpaRepository<Forum, Long> {

    @Override
    @Query("select f from Forum f join fetch f.user where f.id = :forumId")
    Optional<Forum> findById(Long forumId);

    @Query("select f from Forum f join fetch f.user where f.title like CONCAT('%', :title, '%') and f.channelId = :channelId and f.deleted = false")
    Slice<Forum> findForumsByTitleWithChannelId(String title, Long channelId, Pageable pageable);

    @Query("select f from Forum f join fetch f.user where f.channelId = :channelId and f.deleted = false")
    Slice<Forum> findForumsWithChannelId(Long channelId, Pageable pageable);

    @Query("select f from Forum f where f.channelId = :channelId and f.deleted = false")
    List<Forum> findForumsByChannelId(Long channelId);


    @Modifying
    @Query("UPDATE Forum f SET f.deleted = true WHERE f.channelId = :channelId")
    void deleteAllByChannelId(Long channelId);
}
