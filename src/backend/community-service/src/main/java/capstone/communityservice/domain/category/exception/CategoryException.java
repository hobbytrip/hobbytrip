package capstone.communityservice.domain.category.exception;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class CategoryException extends GlobalException {
    public CategoryException(Code errorCode) {
        super(errorCode);
    }

    public CategoryException(Code errorCode, String message) {
        super(errorCode, message);
    }

}
