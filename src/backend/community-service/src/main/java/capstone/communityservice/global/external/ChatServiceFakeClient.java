package capstone.communityservice.global.external;

import capstone.communityservice.global.external.dto.ServerMessageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ChatServiceFakeClient {

    public Page<ServerMessageDto> getMessages(Long channelId){
        return null;
    }
}
