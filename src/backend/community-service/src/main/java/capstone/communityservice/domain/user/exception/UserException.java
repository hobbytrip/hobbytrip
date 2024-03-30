package capstone.communityservice.domain.user.exception;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class UserException extends GlobalException {
    public UserException(Code errorCode) {
        super(errorCode);
    }

    public UserException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
