package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageCreateResponse {

    private Long messageId;
    private Long dmRoomId;
    private Long userId;
    private Long parentId;
    private String profileImage;
    private String writer;
    private String content;
    private boolean isDeleted;
    private ChatType chatType;
    private ActionType actionType;
    private List<UploadFile> files;
    private LocalDateTime createdAt;

    public static DirectMessageCreateResponse from(DirectMessageDto message) {
        return new DirectMessageCreateResponse(
                message.getMessageId(),
                message.getDmRoomId(),
                message.getUserId(),
                message.getParentId(),
                message.getProfileImage(),
                message.getWriter(),
                message.getContent(),
                message.isDeleted(),
                ChatType.DM,
                ActionType.SEND,
                message.getFiles(),
                message.getCreatedAt()
        );
    }
}
