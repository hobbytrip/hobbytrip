package capstone.chatservice.domain.forum.repository;

import capstone.chatservice.domain.forum.domain.ForumMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumMessageRepository extends MongoRepository<ForumMessage, Long> {

    @Query("{ 'forumId': ?0, 'isDeleted': false, 'parentId': 0 }")
    Page<ForumMessage> findByForumIdAndIsDeletedAndParentId(Long forumId, Pageable pageable);

    @Query("{ 'parentId': ?0, 'isDeleted': false }")
    Page<ForumMessage> findByParentIdAndIsDeleted(Long parentId, Pageable pageable);

    @Query(value = "{ 'parentId': { $in: ?0 }, 'isDeleted': false }")
    List<ForumMessage> findCommentCountByParentIdsAndIsDeleted(List<Long> parentIds);
}
