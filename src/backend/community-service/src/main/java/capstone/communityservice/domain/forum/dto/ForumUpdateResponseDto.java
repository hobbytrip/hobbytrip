package capstone.communityservice.domain.forum.dto;

import capstone.communityservice.domain.forum.entity.Forum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumUpdateResponseDto {
    private String title;

    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private List<FileResponseDto> files;

    public static ForumUpdateResponseDto of(Forum forum, List<FileResponseDto> files){
        return ForumUpdateResponseDto.builder()
                .title(forum.getTitle())
                .content(forum.getContent())
                .createAt(forum.getCreatedAt())
                .updateAt(forum.getUpdatedAt())
                .files(files)
                .build();
    }
}
