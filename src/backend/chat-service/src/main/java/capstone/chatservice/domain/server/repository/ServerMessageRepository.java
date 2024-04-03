package capstone.chatservice.domain.server.repository;

import capstone.chatservice.domain.server.domain.ServerMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerMessageRepository extends MongoRepository<ServerMessage, Long> {

}