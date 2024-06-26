package capstone.chatservice.global.exception;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {

    OK(0, HttpStatus.OK, "Ok"),

    BAD_REQUEST(70000, HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(70001, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND(70002, HttpStatus.NOT_FOUND, "Requested resource is not found"),

    FILE_COUNT_EXCEEDED(70003, HttpStatus.BAD_REQUEST, "파일 업로드 가능 개수는 10개 이하 입니다."),
    FILE_SIZE_EXCEEDED(70004, HttpStatus.BAD_REQUEST, "업로드 할 수 있는 파일의 최대 크기는 5MB 입니다."),
    FILE_UPLOAD_FAILED(70005, HttpStatus.BAD_REQUEST, "파일 업로드에 실패했습니다."),
    FILE_NOT_FOUND(70006, HttpStatus.NOT_FOUND, "존재하지 않는 파일입니다."),

    INTERNAL_ERROR(70010, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error"),
    DATA_ACCESS_ERROR(70011, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error"),

    UNAUTHORIZED(70009, HttpStatus.UNAUTHORIZED, "User unauthorized");

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
