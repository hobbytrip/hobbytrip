package capstone.chatservice.domain.server.dto.event;

import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.domain.server.dto.request.ServerMessageTypingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerChatTypingEvent {

    private String uuid;
    private Long serverId;
    private Long channelId;
    private String writer;
    private ChatType chatType;
    private ActionType actionType;

    public static ServerChatTypingEvent from(ServerMessageTypingRequest typingRequest, String uuid) {
        return new ServerChatTypingEvent(
                uuid,
                typingRequest.getServerId(),
                typingRequest.getChannelId(),
                typingRequest.getWriter(),
                ChatType.SERVER,
                ActionType.TYPING
        );
    }
}