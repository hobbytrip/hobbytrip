package capstone.chatservice.domain.emoji.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmojiCreateRequest {

    private Long serverId;
    private Long channelId;
    private Long dmId;
    private Long serverMessageId;
    private Long forumMessageId;
    private Long directMessageId;
    private Long userId;
    private Long typeId;
    private String type;
}