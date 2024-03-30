package capstone.communityservice.domain.server.exception;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ServerException extends GlobalException {
    public ServerException(Code errorCode) {
        super(errorCode);
    }

    public ServerException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
