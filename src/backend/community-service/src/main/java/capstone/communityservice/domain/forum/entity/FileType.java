package capstone.communityservice.domain.forum.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    IMAGE("이미지"), ETC("기타");

    private final String note;
}


//@Getter
//@RequiredArgsConstructor
//public enum ChannelType {
//    VOICE("보이스 채널"), CHAT("채팅 채널"), FORUM("포럼 채널");
//
//    private final String note;
//}