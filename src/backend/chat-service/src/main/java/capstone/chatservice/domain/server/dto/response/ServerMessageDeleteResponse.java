package capstone.chatservice.domain.server.dto.response;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessageDeleteResponse {

    private Long messageId;
    private String type;

    public static ServerMessageDeleteResponse from(ServerMessageDto messageDto) {
        return new ServerMessageDeleteResponse(
                messageDto.getMessageId(),
                messageDto.getType()
        );
    }
}
