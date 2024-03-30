package capstone.communityservice.domain.dm.exception;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DmException extends GlobalException {
    public DmException(Code errorCode) {
        super(errorCode);
    }

    public DmException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
