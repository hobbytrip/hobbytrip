package capstone.chatservice.domain.emoji.dto.response;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmojiDeleteResponse {

    private Long emojiId;
    private Long serverId;
    private Long channelId;
    private Long dmId;
    private Long serverMessageId;
    private Long forumMessageId;
    private Long directMessageId;
    private Long userId;
    private Long typeId;
    private String type;

    public static EmojiDeleteResponse from(EmojiDto emojiDto) {
        return new EmojiDeleteResponse(
                emojiDto.getEmojiId(),
                emojiDto.getServerId(),
                emojiDto.getChannelId(),
                emojiDto.getDmId(),
                emojiDto.getServerMessageId(),
                emojiDto.getForumMessageId(),
                emojiDto.getDirectMessageId(),
                emojiDto.getUserId(),
                emojiDto.getTypeId(),
                emojiDto.getType()
        );
    }
}