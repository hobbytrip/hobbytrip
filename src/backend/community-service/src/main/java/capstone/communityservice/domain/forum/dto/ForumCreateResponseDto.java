package capstone.communityservice.domain.forum.dto;

import capstone.communityservice.domain.forum.entity.File;
import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.entity.ForumCategory;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumCreateResponseDto {
    private Long forumId;

    private Long channelId;

    private String title;

    private String writer;

    private String content;

    private ForumCategory forumCategory;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private List<FileResponseDto> files;

    public static ForumCreateResponseDto of(Forum forum, String userName){
        return ForumCreateResponseDto.builder()
                .forumId(forum.getId())
                .channelId(forum.getChannelId())
                .title(forum.getTitle())
                .writer(userName)
                .content(forum.getContent())
                .forumCategory(forum.getCategory())
                .createAt(forum.getCreatedAt())
                .updateAt(forum.getUpdatedAt())
                .files(forum.
                        getFiles().
                        stream().
                        map(FileResponseDto::of).
                        toList()
                )
                .build();
    }

}
