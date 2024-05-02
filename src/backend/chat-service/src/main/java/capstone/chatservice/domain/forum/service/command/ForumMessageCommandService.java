package capstone.chatservice.domain.forum.service.command;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.request.ForumMessageCreateRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageDeleteRequest;
import capstone.chatservice.domain.forum.dto.request.ForumMessageModifyRequest;

public interface ForumMessageCommandService {

    ForumMessageDto save(ForumMessageCreateRequest createRequest);

    ForumMessageDto modify(ForumMessageModifyRequest modifyRequest);

    ForumMessageDto delete(ForumMessageDeleteRequest deleteRequest);
}
