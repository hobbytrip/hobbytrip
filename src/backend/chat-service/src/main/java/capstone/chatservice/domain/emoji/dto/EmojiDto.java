package capstone.chatservice.domain.emoji.dto;

import capstone.chatservice.domain.emoji.domain.Emoji;
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
        return EmojiDto.builder()
                .emojiId(emoji.getEmojiId())
                .serverId(emoji.getServerId())
                .channelId(emoji.getChannelId())
                .dmId(emoji.getDmId())
                .serverMessageId(emoji.getServerMessageId())
                .forumMessageId(emoji.getForumMessageId())
                .directMessageId(emoji.getDirectMessageId())
                .userId(emoji.getUserId())
                .typeId(emoji.getTypeId())
                .type(emoji.getType())
                .build();
    }
}
