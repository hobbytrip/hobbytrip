package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageDeleteResponse {

    private Long messageId;
    private String type;

    public static DirectMessageDeleteResponse from(DirectMessageDto messageDto) {
        return new DirectMessageDeleteResponse(
                messageDto.getMessageId(),
                messageDto.getType()
        );
    }
}
