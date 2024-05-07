package capstone.communityservice.global.external;

import capstone.communityservice.global.external.dto.DmMessageDto;
import capstone.communityservice.global.external.dto.ServerMessageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ChatServiceFakeClient {

    public Page<ServerMessageDto> getServerMessages(Long channelId){
        return null;
    }

    public Page<DmMessageDto> getDmMessages(Long dmId){
        return null;
    }
}
