package com.capstone.userservice.domain.profile.exception;

import com.capstone.userservice.global.exception.Code;
import com.capstone.userservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class S3Exception extends GlobalException {

    public S3Exception(Code errorCode) {
        super(errorCode);
    }

    public S3Exception(Code errorCode, String message) {
        super(errorCode, message);
    }
}
