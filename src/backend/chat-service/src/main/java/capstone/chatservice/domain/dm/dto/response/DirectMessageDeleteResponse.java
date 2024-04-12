package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
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
public class DirectMessageDeleteResponse {

    private Long messageId;
    private String type;

    public static DirectMessageDeleteResponse from(DirectMessageDto messageDto) {
        return DirectMessageDeleteResponse.builder()
                .messageId(messageDto.getMessageId())
                .type(messageDto.getType())
                .build();
    }
}
