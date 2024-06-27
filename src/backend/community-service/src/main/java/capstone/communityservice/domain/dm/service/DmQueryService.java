package capstone.communityservice.domain.dm.service;

import capstone.communityservice.domain.dm.dto.response.DmReadResponse;
import capstone.communityservice.domain.dm.dto.response.DmResponse;
import capstone.communityservice.domain.dm.dto.response.DmUserInfo;
import capstone.communityservice.domain.dm.entity.Dm;
import capstone.communityservice.domain.dm.repository.DmRepository;
import capstone.communityservice.domain.dm.repository.DmUserRepository;
import capstone.communityservice.domain.server.exception.ServerException;
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
    public DmReadResponse read(Long dmId) {
        Dm findDm = findDmWithDmUser(dmId);
        List<Long> userIds = dmUserRepository.findUserIdsByDmId(dmId);

        UserConnectionStateResponse usersConnectionState = stateServiceClient.getUsersConnectionState(userIds);

        Page<DmMessageDto> messages = chatServiceClient.getDmMessages(
                findDm.getId(),
                0,
                30
        );

        List<DmUserInfo> dmUserInfos = findDm
                .getDmUsers()
                .stream()
                .map(DmUserInfo::of)
                .toList();

        return DmReadResponse.of(
                DmResponse.of(findDm),
                dmUserInfos,
                usersConnectionState,
                messages
        );
    }

    private Dm findDmWithDmUser(Long dmId) {
        return dmRepository.findDmWithUserById(dmId)
                .orElseThrow(() ->
                        new ServerException(Code.NOT_FOUND, "Server Not Found")
                );
    }
}
