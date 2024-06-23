package capstone.chatservice.domain.dm.service.command;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageDeleteRequest;
import capstone.chatservice.domain.dm.dto.request.DirectMessageModifyRequest;

public interface DirectMessageCommandService {

    DirectMessageDto save(DirectMessageCreateRequest createRequest);

    DirectMessageDto modify(DirectMessageModifyRequest modifyRequest);

    DirectMessageDto delete(DirectMessageDeleteRequest deleteRequest);
}
