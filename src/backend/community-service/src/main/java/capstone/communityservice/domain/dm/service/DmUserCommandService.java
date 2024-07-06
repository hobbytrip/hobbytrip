package capstone.communityservice.domain.dm.service;

import capstone.communityservice.domain.dm.dto.request.DmUserDeleteRequest;
import capstone.communityservice.domain.dm.entity.DmUser;
import capstone.communityservice.domain.dm.exception.DmException;
import capstone.communityservice.domain.dm.repository.DmUserRepository;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DmUserCommandService {

    private final DmUserRepository dmUserRepository;

    public void delete(DmUserDeleteRequest request) {
        DmUser dmUser = validateDmUser(request.getDmId(), request.getUserId());

        dmUserRepository.delete(dmUser);
    }

    private DmUser validateDmUser(Long dmId, Long userId){
        return dmUserRepository.findByDmIdAndUserId(dmId, userId)
                .orElseThrow(() -> new DmException(Code.NOT_FOUND, "Not Found DmUser"));
    }
}
