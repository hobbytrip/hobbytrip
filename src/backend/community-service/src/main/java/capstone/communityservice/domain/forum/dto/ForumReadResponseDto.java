package capstone.communityservice.domain.forum.dto;

import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.global.external.dto.ForumMessageDto;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumReadResponseDto {

    private ForumResponseDto forum;
    private Page<ForumMessageDto> messages;

    public static ForumReadResponseDto of(ForumResponseDto forum, Page<ForumMessageDto> messages){
        return ForumReadResponseDto.builder()
                .forum(forum)
                .messages(messages)
                .build();
    }
}
