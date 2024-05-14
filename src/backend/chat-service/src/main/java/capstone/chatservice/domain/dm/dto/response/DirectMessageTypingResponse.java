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
public class DirectMessageTypingResponse {

    private Long dmRoomId;
    private String writer;
    private String type;

    public static DirectMessageTypingResponse from(DirectMessageDto directMessageDto) {
        return new DirectMessageTypingResponse(
                directMessageDto.getDmRoomId(),
                directMessageDto.getWriter(),
                directMessageDto.getType()
        );
    }
}
