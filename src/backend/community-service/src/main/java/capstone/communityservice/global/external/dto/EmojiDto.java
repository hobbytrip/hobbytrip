package capstone.communityservice.global.external.dto;

import lombok.Getter;

@Getter
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
}
