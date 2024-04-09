package capstone.chatservice.domain.emoji.dto.response;

import capstone.chatservice.domain.emoji.dto.EmojiDto;
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
        return EmojiDeleteResponse.builder()
                .emojiId(emojiDto.getEmojiId())
                .serverId(emojiDto.getServerId())
                .channelId(emojiDto.getChannelId())
                .dmId(emojiDto.getDmId())
                .serverMessageId(emojiDto.getServerMessageId())
                .forumMessageId(emojiDto.getForumMessageId())
                .directMessageId(emojiDto.getDirectMessageId())
                .userId(emojiDto.getUserId())
                .typeId(emojiDto.getTypeId())
                .type(emojiDto.getType())
                .build();
    }
}