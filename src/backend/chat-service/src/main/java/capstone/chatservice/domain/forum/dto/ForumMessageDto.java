package capstone.chatservice.domain.forum.dto;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.forum.domain.ForumMessage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumMessageDto {

    private Long messageId;
    private Long forumId;
    private Long serverId;
    private Long channelId;
    private Long userId;
    private Long parentId;
    private Long count;
    private String profileImage;
    private String type;
    private String writer;
    private String content;
    private boolean isDeleted;
    private List<String> files;
    private List<EmojiDto> emojis;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ForumMessageDto from(ForumMessage message) {
        return new ForumMessageDto(
                message.getMessageId(),
                message.getForumId(),
                message.getServerId(),
                message.getChannelId(),
                message.getUserId(),
                message.getParentId(),
                0L,
                message.getProfileImage(),
                message.getType(),
                message.getWriter(),
                message.getContent(),
                message.isDeleted(),
                message.getFiles(),
                null,
                message.getCreatedAt(),
                message.getModifiedAt()
        );
    }
}