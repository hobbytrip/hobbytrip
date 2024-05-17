package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageDeleteResponse {

    private Long messageId;
    private ChatType chatType;
    private ActionType actionType;

    public static DirectMessageDeleteResponse from(DirectMessageDto messageDto) {
        return new DirectMessageDeleteResponse(
                messageDto.getMessageId(),
                ChatType.DM,
                ActionType.DELETE
        );
    }
}
