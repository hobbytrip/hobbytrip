package capstone.communityservice.domain.server.exception;

import capstone.communityservice.global.exception.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ServerException extends RuntimeException{
    private final Code errorCode;

    public ServerException() {
        super(Code.INTERNAL_ERROR.getMessage());
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ServerException(String message) {
        super(Code.INTERNAL_ERROR.getMessage(message));
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ServerException(String message, Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ServerException(Throwable cause) {
        super(Code.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = Code.INTERNAL_ERROR;
    }

    public ServerException(Code errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServerException(Code errorCode, String message) {
        super(errorCode.getMessage(message));
        this.errorCode = errorCode;
    }

    public ServerException(Code errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    public ServerException(Code errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }
}
