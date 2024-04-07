package capstone.chatservice.domain.server.domain;

import capstone.chatservice.domain.model.BaseModel;
import java.time.LocalDateTime;
import java.util.List;
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
@Document(collection = "serverMessages")
public class ServerMessage extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "serverMessages_sequence";

    @Id
    private Long messageId;

    @Field
    private Long serverId;

    @Field
    private Long channelId;

    @Field
    private Long userId;

    @Field
    private Long parentId = 0L;

    @Field
    private String profileImage;

    @Field
    private String type;

    @Field
    private String writer;

    @Field
    private String content;

    @Field
    private boolean isDeleted = Boolean.FALSE;

    @Field
    private List<String> files;

    @Builder
    public ServerMessage(Long serverId, Long channelId, Long userId, Long parentId, String profileImage, String type,
                         String writer, String content, List<String> files) {

        this.serverId = serverId;
        this.channelId = channelId;
        this.userId = userId;
        this.parentId = parentId;
        this.profileImage = profileImage;
        this.type = type;
        this.writer = writer;
        this.content = content;
        this.files = files;
        this.setCreatedAt(LocalDateTime.now());
    }

    public void generateSequence(Long messageId) {
        this.messageId = messageId;
    }

    public void modify(String type, String content) {
        this.type = type;
        this.content = content;
        this.setModifiedAt(LocalDateTime.now());
    }
}