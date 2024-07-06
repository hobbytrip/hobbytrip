package capstone.communityservice.domain.forum.service;

import capstone.communityservice.domain.forum.dto.response.ForumReadResponse;
import capstone.communityservice.domain.forum.dto.response.ForumResponse;
import capstone.communityservice.domain.forum.entity.Forum;
import capstone.communityservice.domain.forum.exception.ForumException;
import capstone.communityservice.domain.forum.repository.ForumRepository;
import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.external.ChatServiceClient;
import capstone.communityservice.global.external.dto.ForumMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ForumQueryService {

    private final ForumRepository forumRepository;

     private final ChatServiceClient chatServiceClient;

    public ForumReadResponse read(Long forumId) {
        Forum findForum = validateExistForum(forumId);

        Page<ForumMessageDto> messages = chatServiceClient.getForumMessages(forumId, 0, 30);

        return ForumReadResponse.of(ForumResponse.of(findForum), messages);
    }

    private Forum validateExistForum(Long forumId){
        return forumRepository.findById(forumId)
                .orElseThrow(() ->
                        new ForumException(Code.NOT_FOUND, "Forum Not Found")
                );
    }
}
