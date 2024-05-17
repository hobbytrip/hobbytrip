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
public class ForumMessageTypingResponse {

    private Long serverId;
    private Long forumId;
    private String writer;
    private ChatType chatType;
    private ActionType actionType;

    public static ForumMessageTypingResponse from(ForumMessageDto forumMessageDto) {
        return new ForumMessageTypingResponse(
                forumMessageDto.getServerId(),
                forumMessageDto.getForumId(),
                forumMessageDto.getWriter(),
                ChatType.FORUM,
                ActionType.TYPING
        );
    }
}
