package capstone.communityservice.domain.dm.dto.response;

import capstone.communityservice.global.external.dto.DmMessageDto;
import capstone.communityservice.global.external.dto.UserConnectionStateResponse;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmReadResponse {
    private DmResponse dm;
    private List<DmUserInfo> dmUserInfos;
    private UserConnectionStateResponse userConnectionState;
    private Page<DmMessageDto> messages;

    public static DmReadResponse of(
            DmResponse dm,
            List<DmUserInfo> dmUserInfos,
            UserConnectionStateResponse userConnectionState,
            Page<DmMessageDto> messages)
    {
        return DmReadResponse.builder()
                .dm(dm)
                .dmUserInfos(dmUserInfos)
                .userConnectionState(userConnectionState)
                .messages(messages)
                .build();
    }
}
