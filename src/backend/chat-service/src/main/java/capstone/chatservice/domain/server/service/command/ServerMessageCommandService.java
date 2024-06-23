package capstone.chatservice.domain.server.service.command;

import capstone.chatservice.domain.server.dto.ServerMessageDto;
import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;

public interface ServerMessageCommandService {

    ServerMessageDto save(ServerMessageCreateRequest createRequest);

    ServerMessageDto modify(ServerMessageModifyRequest modifyRequest);

    ServerMessageDto delete(ServerMessageDeleteRequest deleteRequest);
}
