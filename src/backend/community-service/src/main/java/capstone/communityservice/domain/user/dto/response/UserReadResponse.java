package capstone.communityservice.domain.user.dto.response;

import capstone.communityservice.domain.dm.dto.response.DmUserReadResponse;
import capstone.communityservice.domain.server.dto.response.ServerUserReadResponse;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReadResponse {

    private List<ServerUserReadResponse> servers;

    private List<DmUserReadResponse> dms;

    public static UserReadResponse of(List<ServerUserReadResponse> servers, List<DmUserReadResponse> dms){
        return UserReadResponse.builder()
                .servers(servers)
                .dms(dms)
                .build();
    }
}
