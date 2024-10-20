package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.infra.kafka.producer.chat.event.ServerChatEvent;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessageModifyResponse {

    private Long serverId;
    private Long messageId;
    private String content;
    private ChatType chatType;
    private ActionType actionType;
    private LocalDateTime modifiedAt;

    public static ServerMessageModifyResponse from(ServerChatEvent chatEvent) {
        return new ServerMessageModifyResponse(
                chatEvent.getServerId(),
                chatEvent.getMessageId(),
                chatEvent.getContent(),
                chatEvent.getChatType(),
                chatEvent.getActionType(),
                chatEvent.getModifiedAt()
        );
    }
}
