package capstone.chatservice.domain.dm.repository;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, Long> {

    @Query("{ 'dmRoomId': ?0, 'isDeleted': false, 'parentId': 0 }")
    Page<DirectMessage> findByDmRoomIdAndIsDeletedAndParentId(Long dmRoomId, Pageable pageable);

    @Query("{ 'parentId': ?0, 'isDeleted': false }")
    Page<DirectMessage> findByParentIdAndIsDeleted(Long parentId, Pageable pageable);

    @Query(value = "{ 'parentId': { $in: ?0 }, 'isDeleted': false }")
    List<DirectMessage> findCommentCountByParentIdsAndIsDeleted(List<Long> parentIds);
}
