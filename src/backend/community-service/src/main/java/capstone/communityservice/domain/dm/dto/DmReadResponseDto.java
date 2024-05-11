package capstone.communityservice.domain.dm.dto;

import capstone.communityservice.global.external.dto.DmMessageDto;
import capstone.communityservice.global.external.dto.DmUserStateResponseDto;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DmReadResponseDto {
    private DmResponseDto dm;
    private DmUserStateResponseDto userOnOff;
    private Page<DmMessageDto> messages;

    public static DmReadResponseDto of(
            DmResponseDto dm,
            DmUserStateResponseDto userOnOff,
            Page<DmMessageDto> messages)
    {
        return DmReadResponseDto.builder()
                .dm(dm)
                .userOnOff(userOnOff)
                .messages(messages)
                .build();
    }
}
