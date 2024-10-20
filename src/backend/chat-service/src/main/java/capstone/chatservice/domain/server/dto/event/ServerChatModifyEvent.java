package capstone.chatservice.domain.server.dto.event;

import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.domain.server.domain.ServerMessage;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerChatModifyEvent {

    private String uuid;
    private Long serverId;
    private Long messageId;
    private String content;
    private ChatType chatType;
    private ActionType actionType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ServerChatModifyEvent from(ServerMessage message, String uuid) {
        return new ServerChatModifyEvent(
                uuid,
                message.getServerId(),
                message.getMessageId(),
                message.getContent(),
                message.getChatType(),
                message.getActionType(),
                message.getCreatedAt(),
                message.getModifiedAt()
        );
    }
}
