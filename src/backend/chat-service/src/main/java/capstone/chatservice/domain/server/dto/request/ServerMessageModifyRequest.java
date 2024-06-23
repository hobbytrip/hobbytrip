package capstone.chatservice.domain.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerMessageModifyRequest {

    private Long serverId;
    private Long messageId;
    private String content;
    private String type;
}
