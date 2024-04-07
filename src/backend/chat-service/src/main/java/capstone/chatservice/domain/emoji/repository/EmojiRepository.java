package capstone.chatservice.domain.emoji.repository;

import capstone.chatservice.domain.emoji.domain.Emoji;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmojiRepository extends MongoRepository<Emoji, Long> {

}