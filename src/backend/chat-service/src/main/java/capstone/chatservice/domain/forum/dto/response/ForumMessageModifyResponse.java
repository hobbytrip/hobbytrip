package capstone.chatservice.domain.forum.dto.response;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForumMessageModifyResponse {

    private Long messageId;
    private Long serverId;
    private Long forumId;
    private String content;
    private String type;
    private LocalDateTime modifiedAt;

    public static ForumMessageModifyResponse from(ForumMessageDto message) {
        return ForumMessageModifyResponse.builder()
                .messageId(message.getMessageId())
                .serverId(message.getServerId())
                .forumId(message.getForumId())
                .type(message.getType())
                .content(message.getContent())
                .modifiedAt(message.getModifiedAt())
                .build();
    }
}
