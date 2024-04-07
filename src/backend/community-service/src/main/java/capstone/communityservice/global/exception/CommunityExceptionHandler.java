package capstone.communityservice.global.exception;

import capstone.communityservice.domain.category.exception.CategoryException;
import capstone.communityservice.domain.channel.exception.ChannelException;
import capstone.communityservice.domain.dm.exception.DmException;
import capstone.communityservice.domain.forum.exception.ForumException;
import capstone.communityservice.domain.server.exception.ServerException;
import capstone.communityservice.domain.user.exception.UserException;
import capstone.communityservice.global.common.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CommunityExceptionHandler{

    @ExceptionHandler(CategoryException.class)
    protected ErrorResponseDto handleCategoryException(CategoryException e){
        log.error("CategoryException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(ChannelException.class)
    protected ErrorResponseDto handleChannelException(ChannelException e){
        log.error("CategoryException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(DmException.class)
    protected ErrorResponseDto handleDmException(DmException e){
        log.error("DmException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(ForumException.class)
    protected ErrorResponseDto handleForumException(ForumException e){
        log.error("ForumException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(ServerException.class)
    protected ErrorResponseDto handleServerException(ServerException e){
        log.error("ServerException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    protected ErrorResponseDto handleUserException(UserException e){
        log.error("UserException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(GlobalException.class)
    protected ErrorResponseDto handleGlobalException(GlobalException e){
        log.error("GlobalException: {}", e.getErrorCode().getMessage());
        return ErrorResponseDto.of(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getDetailMessageArguments());
        return ErrorResponseDto.of(Code.VALIDATION_ERROR);
    }
}
