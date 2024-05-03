package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
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
    private String type;
    private String writer;
    private String content;
    private boolean isDeleted;
    private List<String> files;
    private LocalDateTime createdAt;

    public static DirectMessageCreateResponse from(DirectMessageDto message) {
        return new DirectMessageCreateResponse(
                message.getMessageId(),
                message.getDmRoomId(),
                message.getUserId(),
                message.getParentId(),
                message.getProfileImage(),
                message.getType(),
                message.getWriter(),
                message.getContent(),
                message.isDeleted(),
                message.getFiles(),
                message.getCreatedAt()
        );
    }
}
