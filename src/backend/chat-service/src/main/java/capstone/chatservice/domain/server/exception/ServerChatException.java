package capstone.chatservice.domain.server.exception;

import capstone.chatservice.global.exception.Code;
import capstone.chatservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class ServerChatException extends GlobalException {

    public ServerChatException(Code errorCode) {
        super(errorCode);
    }

    public ServerChatException(Code errorCode, String message) {
        super(errorCode, message);
    }
}