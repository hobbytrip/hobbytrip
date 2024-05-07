package capstone.chatservice.domain.forum.dto;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.forum.domain.ForumMessage;
import capstone.chatservice.domain.forum.dto.request.ForumMessageTypingRequest;
import capstone.chatservice.domain.model.UploadFile;
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
    private List<UploadFile> files;
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

    public static ForumMessageDto from(ForumMessageTypingRequest typingRequest) {
        return new ForumMessageDto(
                null,
                typingRequest.getForumId(),
                typingRequest.getServerId(),
                null,
                null,
                null,
                0L,
                null,
                typingRequest.getType(),
                typingRequest.getWriter(),
                null,
                false,
                null,
                null,
                null,
                null
        );
    }
}
