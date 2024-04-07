package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
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
public class ServerMessageDeleteResponse {

    private Long messageId;
    private String type;

    public static ServerMessageDeleteResponse from(ServerMessageDto messageDto) {
        return ServerMessageDeleteResponse.builder()
                .messageId(messageDto.getMessageId())
                .type(messageDto.getType())
                .build();
    }
}
