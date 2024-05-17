package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import capstone.chatservice.domain.server.dto.ServerMessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessageDeleteResponse {

    private Long messageId;
    private ChatType chatType;
    private ActionType actionType;

    public static ServerMessageDeleteResponse from(ServerMessageDto messageDto) {
        return new ServerMessageDeleteResponse(
                messageDto.getMessageId(),
                ChatType.SERVER,
                ActionType.DELETE
        );
    }
}
