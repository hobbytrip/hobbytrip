package capstone.communityservice.domain.channel.exception;

import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.exception.GlobalException;
import lombok.Getter;

@Getter
public class ChannelException extends GlobalException {

    public ChannelException(Code errorCode) {
        super(errorCode);
    }

    public ChannelException(Code errorCode, String message) {
        super(errorCode, message);
    }
}
