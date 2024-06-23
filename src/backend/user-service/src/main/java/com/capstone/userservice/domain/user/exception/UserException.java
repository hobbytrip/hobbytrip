package com.capstone.userservice.domain.user.exception;

import com.capstone.userservice.global.exception.Code;
import com.capstone.userservice.global.exception.GlobalException;
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
