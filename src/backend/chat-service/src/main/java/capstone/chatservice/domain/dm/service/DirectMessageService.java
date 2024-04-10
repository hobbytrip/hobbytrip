package capstone.chatservice.domain.dm.service;

import capstone.chatservice.domain.dm.domain.DirectMessage;
import capstone.chatservice.domain.dm.dto.DirectMessageDto;
import capstone.chatservice.domain.dm.dto.request.DirectMessageCreateRequest;
import capstone.chatservice.domain.dm.repository.DirectMessageRepository;
import capstone.chatservice.global.util.SequenceGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final SequenceGenerator sequenceGenerator;
    private final DirectMessageRepository messageRepository;

    @Transactional
    public DirectMessageDto save(DirectMessageCreateRequest createRequest) {
        DirectMessage directMessage = DirectMessage.builder()
                .dmRoomId(createRequest.getDmRoomId())
                .parentId(createRequest.getParentId())
                .userId(createRequest.getUserId())
                .content(createRequest.getContent())
                .writer(createRequest.getWriter())
                .type(createRequest.getType())
                .profileImage(createRequest.getProfileImage())
                .build();

        directMessage.generateSequence(sequenceGenerator.generateSequence(DirectMessage.SEQUENCE_NAME));

        return DirectMessageDto.from(messageRepository.save(directMessage));
    }
}
