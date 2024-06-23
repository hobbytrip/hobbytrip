package com.capstone.userservice.domain.friend.exception;

import com.capstone.userservice.global.exception.Code;
import com.capstone.userservice.global.exception.GlobalException;

public class FriendException extends GlobalException {

    public FriendException(Code errorCode) {
        super(errorCode);
    }

    public FriendException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
