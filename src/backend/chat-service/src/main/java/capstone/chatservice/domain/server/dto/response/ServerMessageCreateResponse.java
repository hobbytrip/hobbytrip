package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.infra.kafka.producer.chat.event.ServerChatEvent;
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
    private String writer;
    private String content;
    private boolean isDeleted;
    private ChatType chatType;
    private ActionType actionType;
    private List<UploadFile> files;
    private LocalDateTime createdAt;

    public static ServerMessageCreateResponse from(ServerChatEvent chatEvent) {
        return new ServerMessageCreateResponse(
                chatEvent.getMessageId(),
                chatEvent.getServerId(),
                chatEvent.getChannelId(),
                chatEvent.getUserId(),
                chatEvent.getParentId(),
                0L,
                chatEvent.getProfileImage(),
                chatEvent.getWriter(),
                chatEvent.getContent(),
                chatEvent.isDeleted(),
                chatEvent.getChatType(),
                chatEvent.getActionType(),
                chatEvent.getFiles(),
                chatEvent.getCreatedAt()
        );
    }
}
