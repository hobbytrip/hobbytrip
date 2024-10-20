package capstone.chatservice.domain.server.service.command;

import capstone.chatservice.domain.server.dto.request.ServerMessageCreateRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageDeleteRequest;
import capstone.chatservice.domain.server.dto.request.ServerMessageModifyRequest;

public interface ServerMessageCommandService {

    void save(ServerMessageCreateRequest createRequest);

    void modify(ServerMessageModifyRequest modifyRequest);

    void delete(ServerMessageDeleteRequest deleteRequest);
}
