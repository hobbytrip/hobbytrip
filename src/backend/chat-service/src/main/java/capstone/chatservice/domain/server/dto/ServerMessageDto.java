package capstone.chatservice.domain.server.dto;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import capstone.chatservice.domain.server.domain.ServerMessage;
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
    private String type;
    private String writer;
    private String content;
    private boolean isDeleted;
    private List<String> files;
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
