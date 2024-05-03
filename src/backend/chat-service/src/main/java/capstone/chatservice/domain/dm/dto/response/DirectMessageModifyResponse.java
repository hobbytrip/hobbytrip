package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageModifyResponse {

    private Long dmRoomId;
    private Long messageId;
    private String content;
    private String type;
    private LocalDateTime modifiedAt;

    public static DirectMessageModifyResponse from(DirectMessageDto message) {
        return new DirectMessageModifyResponse(
                message.getDmRoomId(),
                message.getMessageId(),
                message.getType(),
                message.getContent(),
                message.getModifiedAt()
        );
    }
}
