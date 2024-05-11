package capstone.communityservice.global.common.dto.kafka;

import capstone.communityservice.domain.forum.dto.FileResponseDto;
import capstone.communityservice.domain.forum.entity.Forum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommunityForumEventDto {
    private String type;

    private Long serverId;

    private Long forumId;

    private Long channelId;

    private String title;

    private String writer;

    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private List<FileResponseDto> files;

    public static CommunityForumEventDto of(String type, Long serverId, Forum forum, Long channelId, List<FileResponseDto> files){
        return CommunityForumEventDto.builder()
                .type(type)
                .serverId(serverId)
                .forumId(forum.getId())
                .channelId(channelId)
                .title(forum.getTitle())
                .writer(forum.getUser().getName())
                .content(forum.getContent())
                .createAt(forum.getCreatedAt())
                .updateAt(forum.getUpdatedAt())
                .files(files)
                .build();
    }

    public static CommunityForumEventDto of(String type, Long serverId, Long channelId, Long forumId){
        return CommunityForumEventDto.builder()
                .type(type)
                .serverId(serverId)
                .channelId(channelId)
                .forumId(forumId)
                .build();
    }
}
