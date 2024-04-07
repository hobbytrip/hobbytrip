package capstone.communityservice.domain.channel.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChannelType {
    VOICE("보이스 채널"), CHAT("채팅 채널"), FORUM("포럼 채널");

    private final String note;
}
