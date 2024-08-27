package com.capstone.notificationservice.domain.fcm.exception;

import com.capstone.notificationservice.global.exception.Code;
import com.capstone.notificationservice.global.exception.GlobalException;

public class FcmException extends GlobalException {
    public FcmException(Code errorCode) {
        super(errorCode);
    }

    public FcmException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
