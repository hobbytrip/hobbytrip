package capstone.chatservice.domain.server.dto;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.file.domain.UploadFile;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.server.domain.ServerMessage;
import capstone.chatservice.domain.server.dto.request.ServerMessageTypingRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessageDto {

    private Long messageId;
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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ServerMessageDto from(ServerMessage message) {
        return new ServerMessageDto(
                message.getMessageId(),
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
                message.getCreatedAt(),
                message.getModifiedAt()
        );
    }

    public static ServerMessageDto from(ServerMessageTypingRequest typingRequest) {
        return new ServerMessageDto(
                null,
                typingRequest.getServerId(),
                typingRequest.getChannelId(),
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
                null
        );
    }
}
