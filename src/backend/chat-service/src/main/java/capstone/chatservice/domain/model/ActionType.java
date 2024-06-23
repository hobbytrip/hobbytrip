package capstone.chatservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {

    SEND("전송"), MODIFY("수정"), DELETE("삭제"), TYPING("타이핑");

    private final String type;
}
