package capstone.chatservice.domain.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessageTypingRequest {

    private Long serverId;
    private Long channelId;
    private String writer;
    private String type;
}
