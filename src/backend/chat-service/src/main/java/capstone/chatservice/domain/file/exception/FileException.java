package capstone.chatservice.domain.file.exception;

import capstone.chatservice.global.exception.Code;
import capstone.chatservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class FileException extends GlobalException {

    public FileException(Code errorCode) {
        super(errorCode);
    }

    public FileException(Code errorCode, String message) {
        super(errorCode, message);
    }
}