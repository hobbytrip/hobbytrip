package capstone.chatservice.domain.dm.domain;

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
@Document(collection = "directMessages")
public class DirectMessage extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "directMessages_sequence";

    @Id
    private Long messageId;

    @Field
    private Long dmRoomId;

    @Field
    private Long parentId;

    @Field
    private Long userId;

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
    public DirectMessage(Long dmRoomId, Long parentId, Long userId, String profileImage,
                         String writer, String content, ChatType chatType, ActionType actionType,
                         List<UploadFile> files) {

        this.dmRoomId = dmRoomId;
        this.parentId = parentId;
        this.userId = userId;
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