package capstone.chatservice.global.exception.handler;

import capstone.chatservice.domain.file.exception.FileException;
import capstone.chatservice.global.common.dto.ErrorResponseDto;
import capstone.chatservice.global.exception.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class FileExceptionHandler {

    /**
     * 파일 업로드 관련 예외 처리
     */
    @ExceptionHandler(FileException.class)
    protected ErrorResponseDto handleFileException(FileException e) {

        log.error("FileException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    /**
     * 파일 업로드 용량 초과 예외 처리
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ErrorResponseDto handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {

        log.error("FILE_SIZE_EXCEEDED: {}", e.getMessage());
        return ErrorResponseDto.of(Code.FILE_SIZE_EXCEEDED);
    }
}
