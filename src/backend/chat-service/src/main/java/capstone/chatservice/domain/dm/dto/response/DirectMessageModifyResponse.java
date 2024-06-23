package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageModifyResponse {

    private Long dmRoomId;
    private Long messageId;
    private String content;
    private ChatType chatType;
    private ActionType actionType;
    private LocalDateTime modifiedAt;

    public static DirectMessageModifyResponse from(DirectMessageDto message) {
        return new DirectMessageModifyResponse(
                message.getDmRoomId(),
                message.getMessageId(),
                message.getContent(),
                ChatType.DM,
                ActionType.MODIFY,
                message.getModifiedAt()
        );
    }
}
