package capstone.communityservice.domain.user.dto;

import capstone.communityservice.domain.dm.dto.DmReadQueryDto;
import capstone.communityservice.domain.server.dto.ServerReadQueryDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReadResponseDto {

    private List<ServerReadQueryDto> servers;

    private List<DmReadQueryDto> dms;

    public static UserReadResponseDto of(List<ServerReadQueryDto> servers, List<DmReadQueryDto> dms){
        return UserReadResponseDto.builder()
                .servers(servers)
                .dms(dms)
                .build();
    }
}
