package com.capstone.notificationservice.domain.server.exception;

import com.capstone.notificationservice.global.exception.Code;
import com.capstone.notificationservice.global.exception.GlobalException;

public class ServerException extends GlobalException {

    public ServerException(Code errorCode) {
        super(errorCode);
    }

    public ServerException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
