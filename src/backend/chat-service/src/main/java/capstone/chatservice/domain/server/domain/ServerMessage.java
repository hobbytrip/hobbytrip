package capstone.chatservice.domain.server.domain;

import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.BaseModel;
import capstone.chatservice.domain.model.ChatType;
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
    private String writer;

    @Field
    private String content;

    @Field
    private ChatType chatType;

    @Field
    private ActionType actionType;

    @Field
    private boolean isDeleted = Boolean.FALSE;

    @Field
    private List<UploadFile> files;

    @Builder
    public ServerMessage(Long serverId, Long channelId, Long userId, Long parentId, String profileImage,
                         String writer, String content, ChatType chatType, ActionType actionType,
                         List<UploadFile> files) {

        this.serverId = serverId;
        this.channelId = channelId;
        this.userId = userId;
        this.parentId = parentId;
        this.profileImage = profileImage;
        this.writer = writer;
        this.content = content;
        this.files = files;
        this.chatType = chatType;
        this.actionType = actionType;
        this.setCreatedAt(LocalDateTime.now());
    }

    public void generateSequence(Long messageId) {
        this.messageId = messageId;
    }

    public void modify(String content) {
        this.content = content;
        this.actionType = ActionType.MODIFY;
        this.setModifiedAt(LocalDateTime.now());
    }

    public void delete() {
        this.isDeleted = true;
        this.actionType = ActionType.DELETE;
    }
}