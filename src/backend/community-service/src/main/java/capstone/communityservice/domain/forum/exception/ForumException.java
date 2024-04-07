package capstone.communityservice.domain.forum.exception;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ForumException extends GlobalException {
    public ForumException(Code errorCode) {
        super(errorCode);
    }

    public ForumException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
