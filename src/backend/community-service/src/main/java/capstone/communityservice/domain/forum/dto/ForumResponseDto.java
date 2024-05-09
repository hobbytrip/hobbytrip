package capstone.communityservice.domain.forum.dto;

import capstone.communityservice.domain.forum.entity.Forum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumResponseDto {
    private Long forumId;

    private Long channelId;

    private String title;

    private String writer;

    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private List<FileResponseDto> files;

    public static ForumResponseDto of(Forum forum){
        return ForumResponseDto.builder()
                .forumId(forum.getId())
                .channelId(forum.getChannelId())
                .title(forum.getTitle())
                .writer(forum.getUser().getName())
                .content(forum.getContent())
                .createAt(forum.getCreatedAt())
                .updateAt(forum.getUpdatedAt())
                .files(forum.getFiles()
                        .stream()
                        .map(FileResponseDto::of)
                        .toList()
                )
                .build();
    }
}
