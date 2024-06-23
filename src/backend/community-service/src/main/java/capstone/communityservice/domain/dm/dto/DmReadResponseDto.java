package capstone.communityservice.domain.dm.dto;

import capstone.communityservice.global.external.dto.DmMessageDto;
import capstone.communityservice.global.external.dto.UserConnectionStateResponse;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmReadResponseDto {
    private DmResponseDto dm;
    private UserConnectionStateResponse userConnectionState;
    private Page<DmMessageDto> messages;

    public static DmReadResponseDto of(
            DmResponseDto dm,
            UserConnectionStateResponse userConnectionState,
            Page<DmMessageDto> messages)
    {
        return DmReadResponseDto.builder()
                .dm(dm)
                .userConnectionState(userConnectionState)
                .messages(messages)
                .build();
    }
}
