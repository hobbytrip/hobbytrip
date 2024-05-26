package capstone.chatservice.domain.forum.service.query;

import capstone.chatservice.domain.forum.dto.ForumMessageDto;
import capstone.chatservice.domain.forum.dto.response.ForumChannelResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ForumMessageQueryService {

    Page<ForumMessageDto> getMessages(Long forumId, int page, int size);

    Page<ForumMessageDto> getComments(Long parentId, int page, int size);

    ForumChannelResponseDto getForumsMessageCount(List<Long> forumIds);
}
