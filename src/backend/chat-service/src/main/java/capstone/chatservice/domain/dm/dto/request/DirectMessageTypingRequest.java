package capstone.chatservice.domain.dm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageTypingRequest {

    private Long dmRoomId;
    private String writer;
    private String type;
}
