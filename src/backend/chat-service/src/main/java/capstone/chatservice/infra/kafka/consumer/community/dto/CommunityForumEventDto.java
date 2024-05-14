package capstone.chatservice.infra.kafka.consumer.community.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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
}
