package capstone.chatservice.infra.kafka.producer.chat.handler;

import capstone.chatservice.infra.kafka.producer.chat.event.ServerChatEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerChatEventRepository extends MongoRepository<ServerChatEvent, Long> {

    Optional<ServerChatEvent> findByUuid(String uuid);
}