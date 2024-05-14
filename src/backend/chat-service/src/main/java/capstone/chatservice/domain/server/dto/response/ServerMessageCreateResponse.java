package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.model.UploadFile;
import capstone.chatservice.domain.server.dto.ServerMessageDto;
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
public class ServerMessageCreateResponse {

    private Long messageId;
    private Long serverId;
    private Long channelId;
    private Long userId;
    private Long parentId;
    private Long count;
    private String profileImage;
    private String type;
    private String writer;
    private String content;
    private boolean isDeleted;
    private List<UploadFile> files;
    private LocalDateTime createdAt;

    public static ServerMessageCreateResponse from(ServerMessageDto message) {
        return new ServerMessageCreateResponse(
                message.getMessageId(),
                message.getServerId(),
                message.getChannelId(),
                message.getUserId(),
                message.getParentId(),
                0L,
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
