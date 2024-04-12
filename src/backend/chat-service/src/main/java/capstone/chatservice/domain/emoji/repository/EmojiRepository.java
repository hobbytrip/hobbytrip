package capstone.chatservice.domain.emoji.repository;

import capstone.chatservice.domain.emoji.domain.Emoji;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EmojiRepository extends MongoRepository<Emoji, Long> {

    @Query("{'serverMessageId': {$in: ?0}}")
    List<Emoji> findEmojisByServerMessageIds(List<Long> messageIds);

    @Query("{'directMessageId': {$in: ?0}}")
    List<Emoji> findEmojisByDirectMessageIds(List<Long> messageIds);
}