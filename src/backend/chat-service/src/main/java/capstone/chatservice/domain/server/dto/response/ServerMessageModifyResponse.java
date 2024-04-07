package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.server.domain.ServerMessage;
import java.time.LocalDateTime;
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
public class ServerMessageModifyResponse {

    private Long serverId;
    private Long messageId;
    private String content;
    private String type;
    private LocalDateTime modifiedAt;

    public static ServerMessageModifyResponse from(ServerMessage message) {
        return ServerMessageModifyResponse.builder()
                .serverId(message.getServerId())
                .messageId(message.getMessageId())
                .type(message.getType())
                .content(message.getContent())
                .modifiedAt(message.getModifiedAt())
                .build();
    }
}
