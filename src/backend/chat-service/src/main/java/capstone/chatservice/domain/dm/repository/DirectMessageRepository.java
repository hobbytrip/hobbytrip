package capstone.chatservice.domain.dm.repository;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, Long> {
}
