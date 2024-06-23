package capstone.chatservice.domain.forum.dto.response;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
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
public class ForumMessageDeleteResponse {

    private Long messageId;
    private ChatType chatType;
    private ActionType actionType;

    public static ForumMessageDeleteResponse from(ForumMessageDto messageDto) {
        return new ForumMessageDeleteResponse(
                messageDto.getMessageId(),
                ChatType.FORUM,
                ActionType.DELETE
        );
    }
}
