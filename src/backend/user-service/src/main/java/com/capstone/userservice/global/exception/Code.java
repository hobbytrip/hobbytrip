package com.capstone.userservice.global.exception;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {

    // 충돌 방지를 위한 Code format
    // X1XXX: 제이
    // X2XXX: 셀리나
    // X3XXX: 메이슨
    // ex) 메이슨이 닉네임 중복 에러코드를 만든다면
    // USER_NICKNAME_DUPLICATED(13010, HttpStatus.BAD_REQUEST, "User nickname duplicated"),

    OK(0, HttpStatus.OK, "Ok"),
    //100번대
    VALIDATION_ERROR(20011, HttpStatus.BAD_REQUEST, "Validation error"),

    //400번대
    BAD_REQUEST(20040, HttpStatus.BAD_REQUEST, "Bad request"),
    UNAUTHORIZED(20041, HttpStatus.UNAUTHORIZED, "User unauthorized"),
    FORBIDDEN(20043, HttpStatus.FORBIDDEN, "Access denied"),
    NOT_FOUND(20044, HttpStatus.NOT_FOUND, "Requested resource is not found"),
    //500번대
    INTERNAL_ERROR(20050, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(20051, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),
    PUT_OBJECT_EXCEPTION(20052, HttpStatus.INTERNAL_SERVER_ERROR, "Object Put error"),
    IO_EXCEPTION_ON_IMAGE_DELETE(20053, HttpStatus.INTERNAL_SERVER_ERROR, "Image Delete error");


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus) {
        if (httpStatus == null) {
            throw new RuntimeException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return Code.BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return Code.INTERNAL_ERROR;
                    } else {
                        return Code.OK;
                    }
                });
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}