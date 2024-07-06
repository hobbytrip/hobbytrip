package capstone.communityservice.domain.forum.dto.response;

import capstone.communityservice.global.external.dto.ForumMessageDto;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumReadResponse {

    private ForumResponse forum;
    private Page<ForumMessageDto> messages;

    public static ForumReadResponse of(ForumResponse forum, Page<ForumMessageDto> messages){
        return ForumReadResponse.builder()
                .forum(forum)
                .messages(messages)
                .build();
    }
}
