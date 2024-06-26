package capstone.communityservice.domain.dm.service;

import capstone.communityservice.domain.category.dto.CategoryResponseDto;
import capstone.communityservice.domain.channel.dto.ChannelResponseDto;
import capstone.communityservice.domain.dm.dto.DmReadResponseDto;
import capstone.communityservice.domain.dm.dto.DmResponseDto;
import capstone.communityservice.domain.dm.entity.Dm;
import capstone.communityservice.domain.dm.exception.DmException;
import capstone.communityservice.domain.dm.repository.DmRepository;
import capstone.communityservice.domain.dm.repository.DmUserRepository;
import capstone.communityservice.global.exception.Code;
import capstone.communityservice.global.external.ChatServiceClient;
import capstone.communityservice.global.external.StateServiceClient;
import capstone.communityservice.global.external.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DmQueryService {
    private final StateServiceClient stateServiceClient;
    private final ChatServiceClient chatServiceClient;
    private final DmRepository dmRepository;
    private final DmUserRepository dmUserRepository;

    // userId도 추가하여 검증 로직을 해야할지 고민.
    public DmReadResponseDto read(Long dmId) {
        Dm findDm = validateDm(dmId);
        List<Long> userIds = dmUserRepository.findUserIdsByDmId(dmId);

        UserConnectionStateResponse usersConnectionState = stateServiceClient.getUsersConnectionState(userIds);

        Page<DmMessageDto> messages = chatServiceClient.getDmMessages(
                findDm.getId(),
                0,
                30
        );

        return DmReadResponseDto.of(
                DmResponseDto.of(findDm),
                usersConnectionState,
                messages
        );
    }

    private Dm validateDm(Long dmId){
        return dmRepository.findById(dmId)
                .orElseThrow(() -> new DmException(Code.NOT_FOUND, "Not Found Dm"));
    }
}
