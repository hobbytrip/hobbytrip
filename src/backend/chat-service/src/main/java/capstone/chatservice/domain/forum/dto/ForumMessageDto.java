package capstone.chatservice.domain.forum.dto;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.forum.domain.ForumMessage;
import capstone.chatservice.domain.forum.dto.request.ForumMessageTypingRequest;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ForumCategory;
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
    private String writer;
    private String content;
    private boolean isDeleted;
    private ActionType actionType;
    private List<UploadFile> files;
    private List<EmojiDto> emojis;
    private ForumCategory forumCategory;
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
                message.getWriter(),
                message.getContent(),
                message.isDeleted(),
                message.getActionType(),
                message.getFiles(),
                null,
                message.getForumCategory(),
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
                typingRequest.getWriter(),
                null,
                false,
                ActionType.TYPING,
                null,
                null,
                null,
                null,
                null
        );
    }
}
