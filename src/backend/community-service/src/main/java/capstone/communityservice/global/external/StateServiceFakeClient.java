package capstone.communityservice.global.external;

import capstone.communityservice.global.external.dto.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StateServiceFakeClient {

    public ServerUserStateResponseDto checkServerOnOff(ServerUserStateRequestDto requestDto){
//        Map<Long, String> connectionStates;
//        Map<String, Set<String>> channelStates;
        Map<Long, String> connectionStates = new HashMap<>();
        Map<String, Set<String>> channelStates = new HashMap<>();

        List<Long> userIds = requestDto.getUserIds();

        for(Long userId : userIds){
            if(userId % 2 == 1){
                connectionStates.put(userId, "online");
            } else{
                connectionStates.put(userId, "offline");
            }
        }

        return new ServerUserStateResponseDto(connectionStates, channelStates);
    }

    public DmUserStateResponseDto checkDmOnOff(DmUserStateRequestDto requestDto){
        Map<Long, String> connectionStates = new HashMap<>();

        List<Long> userIds = requestDto.getUserIds();

        for(Long userId : userIds){
            if(userId % 2 == 1){
                connectionStates.put(userId, "online");
            } else{
                connectionStates.put(userId, "offline");
            }
        }

        return new DmUserStateResponseDto(connectionStates);
    }

    public ServerUserLocDto userLocation(Long userId){
        return new ServerUserLocDto(1L);
    }
}
