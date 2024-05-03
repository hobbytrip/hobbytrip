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
public class ForumMessageDeleteResponse {

    private Long messageId;
    private String type;

    public static ForumMessageDeleteResponse from(ForumMessageDto messageDto) {
        return new ForumMessageDeleteResponse(
                messageDto.getMessageId(),
                messageDto.getType()
        );
    }
}
