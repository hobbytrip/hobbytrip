package capstone.chatservice.domain.dm.dto;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import capstone.chatservice.domain.dm.dto.request.DirectMessageTypingRequest;
import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.model.ActionType;
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
public class DirectMessageDto {

    private Long messageId;
    private Long parentId;
    private Long dmRoomId;
    private Long userId;
    private Long count;
    private String profileImage;
    private String writer;
    private String content;
    private boolean isDeleted;
    private ActionType actionType;
    private List<UploadFile> files;
    private List<EmojiDto> emojis;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static DirectMessageDto from(DirectMessage message) {
        return new DirectMessageDto(
                message.getMessageId(),
                message.getParentId(),
                message.getDmRoomId(),
                message.getUserId(),
                0L,
                message.getProfileImage(),
                message.getWriter(),
                message.getContent(),
                message.isDeleted(),
                message.getActionType(),
                message.getFiles(),
                null,
                message.getCreatedAt(),
                message.getModifiedAt()
        );
    }

    public static DirectMessageDto from(DirectMessageTypingRequest typingRequest) {
        return new DirectMessageDto(
                null,
                null,
                typingRequest.getDmRoomId(),
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
                null
        );
    }
}
