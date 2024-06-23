package com.capstone.userservice.global.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    // errorCode에 대한 getter
    private final Code errorCode;

    // 기본 생성자에서는 일반적인 에러 코드를 사용
    public GlobalException() {
        super(Code.INTERNAL_ERROR.getMessage());
        this.errorCode = Code.INTERNAL_ERROR;
    }

    // 에러 메시지를 받는 생성자
    public GlobalException(String message) {
        super(message);
        this.errorCode = Code.INTERNAL_ERROR;
    }

    // 에러 메시지와 원인을 받는 생성자
    public GlobalException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = Code.INTERNAL_ERROR;
    }

    // 원인만을 받는 생성자
    public GlobalException(Throwable cause) {
        super(cause);
        this.errorCode = Code.INTERNAL_ERROR;
    }

    // 에러 코드를 지정하는 생성자
    public GlobalException(Code errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 에러 코드와 메시지를 받는 생성자
    public GlobalException(Code errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    // 에러 코드, 메시지, 원인을 받는 생성자
    public GlobalException(Code errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    // 에러 코드와 원인을 받는 생성자
    public GlobalException(Code errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

}