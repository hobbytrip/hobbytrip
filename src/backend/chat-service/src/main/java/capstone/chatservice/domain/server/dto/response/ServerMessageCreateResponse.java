package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
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
public class ServerMessageCreateResponse {

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
    private LocalDateTime createdAt;

    public static ServerMessageCreateResponse from(ServerMessageDto message) {
        return ServerMessageCreateResponse.builder()
                .messageId(message.getMessageId())
                .serverId(message.getServerId())
                .channelId(message.getChannelId())
                .userId(message.getUserId())
                .parentId(message.getParentId())
                .profileImage(message.getProfileImage())
                .type(message.getType())
                .writer(message.getWriter())
                .content(message.getContent())
                .isDeleted(message.isDeleted())
                .files(message.getFiles())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
