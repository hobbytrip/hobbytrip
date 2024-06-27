package capstone.communityservice.domain.forum.dto.response;

import capstone.communityservice.domain.forum.entity.Forum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumUpdateResponse {
    private String title;

    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private List<FileResponse> files;

    public static ForumUpdateResponse of(Forum forum, List<FileResponse> files){
        return ForumUpdateResponse.builder()
                .title(forum.getTitle())
                .content(forum.getContent())
                .createAt(forum.getCreatedAt())
                .updateAt(forum.getUpdatedAt())
                .files(files)
                .build();
    }
}
