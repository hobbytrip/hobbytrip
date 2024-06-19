package com.capstone.notificationservice.domain.user.exception;

import com.capstone.notificationservice.global.exception.Code;
import com.capstone.notificationservice.global.exception.GlobalException;

public class UserException extends GlobalException {

    public UserException(Code errorCode) {
        super(errorCode);
    }

    public UserException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
