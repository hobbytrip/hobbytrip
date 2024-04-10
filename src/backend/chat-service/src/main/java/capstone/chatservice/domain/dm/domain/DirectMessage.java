package capstone.chatservice.domain.dm.domain;

import capstone.chatservice.domain.model.BaseModel;
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
    public DirectMessage(Long dmRoomId, Long parentId, Long userId, String profileImage,
                         String type, String writer, String content, List<String> files) {

        this.dmRoomId = dmRoomId;
        this.parentId = parentId;
        this.userId = userId;
        this.profileImage = profileImage;
        this.type = type;
        this.writer = writer;
        this.content = content;
        this.files = files;
    }
}