package capstone.chatservice.domain.forum.repository;

import capstone.chatservice.domain.forum.domain.ForumMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ForumMessageRepository extends MongoRepository<ForumMessage, Long> {
}
