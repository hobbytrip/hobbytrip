package capstone.chatservice.domain.forum.dto.response;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
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
public class ForumMessageDeleteResponse {

    private Long messageId;
    private String type;

    public static ForumMessageDeleteResponse from(ForumMessageDto messageDto) {
        return ForumMessageDeleteResponse.builder()
                .messageId(messageDto.getMessageId())
                .type(messageDto.getType())
                .build();
    }
}
