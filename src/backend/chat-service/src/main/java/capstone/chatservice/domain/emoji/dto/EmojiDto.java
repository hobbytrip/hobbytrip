package capstone.chatservice.domain.emoji.dto;

import capstone.chatservice.domain.emoji.domain.Emoji;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmojiDto {

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

    public static EmojiDto from(Emoji emoji) {
        return new EmojiDto(
                emoji.getEmojiId(),
                emoji.getServerId(),
                emoji.getChannelId(),
                emoji.getDmId(),
                emoji.getServerMessageId(),
                emoji.getForumMessageId(),
                emoji.getDirectMessageId(),
                emoji.getUserId(),
                emoji.getTypeId(),
                emoji.getType()
        );
    }
}
