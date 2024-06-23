package capstone.chatservice.domain.dm.service.query;

import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import org.springframework.data.domain.Page;

public interface DirectMessageQueryService {

    Page<DirectMessageDto> getDirectMessages(Long roomId, int page, int size);

    Page<DirectMessageDto> getComments(Long parentId, int page, int size);
}
