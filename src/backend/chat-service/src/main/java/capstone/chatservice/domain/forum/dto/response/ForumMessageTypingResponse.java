package capstone.chatservice.domain.forum.dto.response;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
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
    private String type;

    public static ForumMessageTypingResponse from(ForumMessageDto forumMessageDto) {
        return new ForumMessageTypingResponse(
                forumMessageDto.getServerId(),
                forumMessageDto.getForumId(),
                forumMessageDto.getWriter(),
                forumMessageDto.getType()
        );
    }
}
