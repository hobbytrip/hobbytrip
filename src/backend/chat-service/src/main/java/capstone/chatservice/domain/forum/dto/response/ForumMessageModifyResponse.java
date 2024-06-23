package capstone.chatservice.domain.forum.dto.response;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
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
public class ForumMessageModifyResponse {

    private Long messageId;
    private Long serverId;
    private Long forumId;
    private String content;
    private ChatType chatType;
    private ActionType actionType;
    private LocalDateTime modifiedAt;

    public static ForumMessageModifyResponse from(ForumMessageDto message) {
        return new ForumMessageModifyResponse(
                message.getMessageId(),
                message.getServerId(),
                message.getForumId(),
                message.getContent(),
                ChatType.FORUM,
                ActionType.MODIFY,
                message.getModifiedAt()
        );
    }
}
