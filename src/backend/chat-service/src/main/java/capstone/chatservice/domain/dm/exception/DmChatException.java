package capstone.chatservice.domain.dm.exception;

import capstone.chatservice.global.exception.Code;
import capstone.chatservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class DmChatException extends GlobalException {

    public DmChatException(Code errorCode) {
        super(errorCode);
    }

    public DmChatException(Code errorCode, String message) {
        super(errorCode, message);
    }
}