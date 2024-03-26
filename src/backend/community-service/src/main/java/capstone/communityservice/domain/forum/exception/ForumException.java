package capstone.communityservice.domain.forum.exception;

import capstone.communityservice.global.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ForumException extends RuntimeException{
    private final Code errorCode;

    public ForumException() {
        super(Code.INTERNAL_ERROR.getMessage());
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ForumException(String message) {
        super(Code.INTERNAL_ERROR.getMessage(message));
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ForumException(String message, Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ForumException(Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ForumException(Code errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ForumException(Code errorCode, String message) {
        super(errorCode.getMessage(message));
        this.errorCode = errorCode;
    }

    public ForumException(Code errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    public ForumException(Code errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }
}
