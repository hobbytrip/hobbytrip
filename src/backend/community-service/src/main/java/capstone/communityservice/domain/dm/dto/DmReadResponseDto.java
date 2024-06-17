package capstone.communityservice.domain.dm.dto;

import capstone.communityservice.global.external.dto.DmMessageDto;
import capstone.communityservice.global.external.dto.UserConnectionStateResponse;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmReadResponseDto {
    private DmResponseDto dm;
    private List<DmUserInfo> dmUserInfos;
    private UserConnectionStateResponse userConnectionState;
    private Page<DmMessageDto> messages;

    public static DmReadResponseDto of(
            DmResponseDto dm,
            List<DmUserInfo> dmUserInfos,
            UserConnectionStateResponse userConnectionState,
            Page<DmMessageDto> messages)
    {
        return DmReadResponseDto.builder()
                .dm(dm)
                .dmUserInfos(dmUserInfos)
                .userConnectionState(userConnectionState)
                .messages(messages)
                .build();
    }
}
