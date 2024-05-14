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
public class ServerMessageTypingResponse {

    private Long serverId;
    private Long channelId;
    private String writer;
    private String type;

    public static ServerMessageTypingResponse from(ServerMessageDto serverMessageDto) {
        return new ServerMessageTypingResponse(
                serverMessageDto.getServerId(),
                serverMessageDto.getChannelId(),
                serverMessageDto.getWriter(),
                serverMessageDto.getType()
        );
    }
}
