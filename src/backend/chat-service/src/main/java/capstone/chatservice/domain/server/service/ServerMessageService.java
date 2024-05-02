package capstone.chatservice.domain.server.service;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;
import org.springframework.data.domain.Page;

public interface ServerMessageService {

    ServerMessageDto save(ServerMessageCreateRequest createRequest);

    ServerMessageDto modify(ServerMessageModifyRequest modifyRequest);

    ServerMessageDto delete(ServerMessageDeleteRequest deleteRequest);

    Page<ServerMessageDto> getMessages(Long channelId, int page, int size);

    Page<ServerMessageDto> getComments(Long parentId, int page, int size);
}
