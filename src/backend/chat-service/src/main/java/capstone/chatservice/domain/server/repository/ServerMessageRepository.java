package capstone.chatservice.domain.server.repository;

import capstone.chatservice.domain.server.domain.ServerMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerMessageRepository extends MongoRepository<ServerMessage, Long> {

    @Query("{ 'channelId': ?0, 'isDeleted': false, 'parentId': 0 }")
    Page<ServerMessage> findByChannelIdAndIsDeletedAndParentId(Long channelId, Pageable pageable);

    @Query("{ 'parentId': ?0, 'isDeleted': false }")
    Page<ServerMessage> findByParentIdAndIsDeleted(Long parentId, Pageable pageable);

    @Query(value = "{ 'parentId': { $in: ?0 } }")
    List<ServerMessage> countMessagesByParentIds(List<Long> parentIds);
}