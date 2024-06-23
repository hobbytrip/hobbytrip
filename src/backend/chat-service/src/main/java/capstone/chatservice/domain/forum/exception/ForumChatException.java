package capstone.chatservice.domain.forum.exception;

import capstone.chatservice.global.exception.Code;
import capstone.chatservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class ForumChatException extends GlobalException {

    public ForumChatException(Code errorCode) {
        super(errorCode);
    }

    public ForumChatException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
