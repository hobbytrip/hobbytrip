package capstone.communityservice.domain.forum.dto.response;

import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.entity.ForumCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumResponse {
    private Long forumId;

    private Long channelId;

    private String title;

    private String writer;

    private String content;

    private ForumCategory forumCategory;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private List<FileResponse> files;

    private Long forumMessageCount;

    public static ForumResponse of(Forum forum){
        return ForumResponse.builder()
                .forumId(forum.getId())
                .channelId(forum.getChannelId())
                .title(forum.getTitle())
                .writer(forum.getUser().getName())
                .content(forum.getContent())
                .forumCategory(forum.getCategory())
                .createAt(forum.getCreatedAt())
                .updateAt(forum.getUpdatedAt())
                .files(forum.getFiles()
                        .stream()
                        .map(FileResponse::of)
                        .toList()
                )
                .build();
    }

    public static ForumResponse of(Forum forum, Long forumMessageCount){
        return ForumResponse.builder()
                .forumId(forum.getId())
                .channelId(forum.getChannelId())
                .title(forum.getTitle())
                .writer(forum.getUser().getName())
                .content(forum.getContent())
                .forumCategory(forum.getCategory())
                .createAt(forum.getCreatedAt())
                .updateAt(forum.getUpdatedAt())
                .files(forum.getFiles()
                        .stream()
                        .map(FileResponse::of)
                        .toList()
                )
                .forumMessageCount(forumMessageCount)
                .build();
    }
}
