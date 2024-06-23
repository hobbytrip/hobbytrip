package capstone.chatservice.domain.dm.dto.response;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.model.ActionType;
import capstone.chatservice.domain.model.ChatType;
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
    private ChatType chatType;
    private ActionType actionType;
    
    public static DirectMessageTypingResponse from(DirectMessageDto directMessageDto) {
        return new DirectMessageTypingResponse(
                directMessageDto.getDmRoomId(),
                directMessageDto.getWriter(),
                ChatType.DM,
                ActionType.TYPING
        );
    }
}
