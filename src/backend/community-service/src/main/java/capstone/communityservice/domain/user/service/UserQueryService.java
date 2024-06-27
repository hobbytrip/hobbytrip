package capstone.communityservice.domain.user.service;

import capstone.communityservice.domain.dm.dto.response.DmUserReadResponse;
import capstone.communityservice.domain.dm.repository.DmRepository;
import capstone.communityservice.domain.dm.repository.DmUserRepository;
import capstone.communityservice.domain.server.dto.response.ServerUserReadResponse;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.server.repository.ServerUserRepository;
import capstone.communityservice.domain.user.dto.response.UserReadResponse;
import capstone.communityservice.domain.user.dto.response.UserServerDmInfo;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.exception.UserException;
import capstone.communityservice.domain.user.repository.UserRepository;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;
    private final ServerRepository serverRepository;
    private final DmRepository dmRepository;
    private final ServerUserRepository serverUserRepository;
    private final DmUserRepository dmUserRepository;

    public User findUserByOriginalId(Long originalId) {
        return userRepository.findByOriginalId(originalId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "User Not Found"));
    }

    public List<User> findUsers(List<Long> userIds){
        return userRepository.findByOriginalIds(userIds);
    }


    public UserReadResponse read(Long userId) {
        // UserReadResponse.of(dmes, servers)
        List<ServerUserReadResponse> servers = serverRepository.findServersWithUserId(userId)
                .stream()
                .map(ServerUserReadResponse::of)
                .toList();

        List<DmUserReadResponse> dms = dmRepository.findDmsWithUserId(userId)
                .stream()
                .map(DmUserReadResponse::of)
                .toList();

        return UserReadResponse.of(servers, dms);
    }

    public UserServerDmInfo feignRead(Long userId) {
        List<Long> serverIds = serverUserRepository.findServerIdsByUserId(userId);

        List<Long> dmIds = dmUserRepository.findDmIdsByUserId(userId);

        return UserServerDmInfo.of(serverIds, dmIds);
    }
}
