package capstone.communityservice.domain.dm.exception;

import capstone.communityservice.global.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DmException extends RuntimeException{
    private final Code errorCode;

    public DmException() {
        super(Code.INTERNAL_ERROR.getMessage());
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public DmException(String message) {
        super(Code.INTERNAL_ERROR.getMessage(message));
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public DmException(String message, Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public DmException(Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public DmException(Code errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public DmException(Code errorCode, String message) {
        super(errorCode.getMessage(message));
        this.errorCode = errorCode;
    }

    public DmException(Code errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    public DmException(Code errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }
}
