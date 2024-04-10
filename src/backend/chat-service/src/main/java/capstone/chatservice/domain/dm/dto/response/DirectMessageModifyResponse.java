package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
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
public class DirectMessageModifyResponse {

    private Long dmRoomId;
    private Long messageId;
    private String content;
    private String type;
    private LocalDateTime modifiedAt;

    public static DirectMessageModifyResponse from(DirectMessageDto message) {
        return DirectMessageModifyResponse.builder()
                .dmRoomId(message.getDmRoomId())
                .messageId(message.getMessageId())
                .type(message.getType())
                .content(message.getContent())
                .modifiedAt(message.getModifiedAt())
                .build();
    }
}
