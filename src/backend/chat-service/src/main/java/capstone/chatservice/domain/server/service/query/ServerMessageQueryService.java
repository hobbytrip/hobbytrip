package capstone.chatservice.domain.server.service.query;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import org.springframework.data.domain.Page;

public interface ServerMessageQueryService {

    Page<ServerMessageDto> getMessages(Long channelId, int page, int size);

    Page<ServerMessageDto> getComments(Long parentId, int page, int size);
}
