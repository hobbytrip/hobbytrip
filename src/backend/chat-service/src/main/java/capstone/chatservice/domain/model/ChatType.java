package capstone.chatservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatType {

    SERVER("서버 채팅 메시지"), DM("DM 채팅 메시지"), FORUM("포럼 채팅 메시지");

    private final String type;
}
