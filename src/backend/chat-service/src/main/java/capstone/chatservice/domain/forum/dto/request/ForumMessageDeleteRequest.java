package capstone.chatservice.domain.forum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumMessageDeleteRequest {

    private Long serverId;
    private Long messageId;
    private String type;
}
