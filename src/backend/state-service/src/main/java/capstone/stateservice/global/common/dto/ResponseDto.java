package capstone.stateservice.global.common.dto;

import capstone.stateservice.global.exception.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto {

    private final Boolean success;
    private final Integer code;
    private final String message;

    public static ResponseDto of(Boolean success, Code code) {
        return new ResponseDto(success, code.getCode(), code.getMessage());
    }

    public static ResponseDto of(Boolean success, Code code, Exception e) {
        return new ResponseDto(success, code.getCode(), code.getMessage(e));
    }

    public static ResponseDto of(Boolean success, Code code, String message) {
        return new ResponseDto(success, code.getCode(), code.getMessage(message));
    }
}
