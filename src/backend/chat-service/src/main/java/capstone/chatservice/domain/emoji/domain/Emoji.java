package capstone.chatservice.domain.emoji.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "emojis")
public class Emoji {

    @Transient
    public static final String SEQUENCE_NAME = "emoji_sequence";

    @Id
    private Long emojiId;

    @Field
    private Long serverId = 0L;

    @Field
    private Long channelId = 0L;

    @Field
    private Long dmId = 0L;

    @Field
    private Long serverMessageId = 0L;

    @Field
    private Long forumMessageId = 0L;

    @Field
    private Long directMessageId = 0L;

    @Field
    private Long userId;

    @Field
    private Long typeId;

    @Field
    private String type;

    @Builder
    public Emoji(Long serverId, Long channelId, Long dmId, Long serverMessageId, Long forumMessageId,
                 Long directMessageId, Long userId, Long typeId, String type) {

        this.serverId = serverId;
        this.channelId = channelId;
        this.dmId = dmId;
        this.serverMessageId = serverMessageId;
        this.forumMessageId = forumMessageId;
        this.directMessageId = directMessageId;
        this.userId = userId;
        this.typeId = typeId;
        this.type = type;
    }

    public void generateSequence(Long emojiId) {
        this.emojiId = emojiId;
    }
}
