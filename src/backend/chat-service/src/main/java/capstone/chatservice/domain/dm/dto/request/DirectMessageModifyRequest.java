package capstone.chatservice.domain.dm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageModifyRequest {

    private Long dmRoomId;
    private Long messageId;
    private String content;
    private String type;
}
