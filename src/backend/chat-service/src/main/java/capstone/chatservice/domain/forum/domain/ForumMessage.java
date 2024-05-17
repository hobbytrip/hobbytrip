package capstone.chatservice.domain.forum.domain;

import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.BaseModel;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.domain.model.UploadFile;
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
@Document(collection = "forumMessages")
public class ForumMessage extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "forumMessages_sequence";

    @Id
    private Long messageId;

    @Field
    private Long serverId;

    @Field
    private Long channelId;

    @Field
    private Long forumId;

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
    public ForumMessage(Long serverId, Long channelId, Long forumId, Long userId,
                        Long parentId, String profileImage, ChatType chatType, ActionType actionType,
                        String writer, String content, List<UploadFile> files) {

        this.serverId = serverId;
        this.channelId = channelId;
        this.forumId = forumId;
        this.userId = userId;
        this.parentId = parentId;
        this.profileImage = profileImage;
        this.writer = writer;
        this.content = content;
        this.chatType = chatType;
        this.actionType = actionType;
        this.files = files;
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
        this.actionType = ActionType.DELETE;
        this.isDeleted = true;
    }
}
