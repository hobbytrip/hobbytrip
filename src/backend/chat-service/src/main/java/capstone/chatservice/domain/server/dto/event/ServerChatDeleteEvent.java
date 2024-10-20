package capstone.chatservice.domain.server.dto.event;

import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.domain.server.domain.ServerMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerChatDeleteEvent {

    private String uuid;
    private Long messageId;
    private ChatType chatType;
    private ActionType actionType;

    public static ServerChatDeleteEvent from(ServerMessage message, String uuid) {
        return new ServerChatDeleteEvent(
                uuid,
                message.getMessageId(),
                message.getChatType(),
                message.getActionType()
        );
    }
}