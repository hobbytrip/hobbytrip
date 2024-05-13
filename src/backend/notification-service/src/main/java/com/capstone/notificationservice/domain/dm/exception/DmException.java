package com.capstone.notificationservice.domain.dm.exception;

import com.capstone.notificationservice.global.exception.Code;
import com.capstone.notificationservice.global.exception.GlobalException;

public class DmException extends GlobalException {
    public DmException(Code errorCode) {
        super(errorCode);
    }

    public DmException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
