package capstone.communityservice.domain.user.service;

import capstone.communityservice.domain.dm.dto.DmReadQueryDto;
import capstone.communityservice.domain.dm.repository.DmRepository;
import capstone.communityservice.domain.server.dto.ServerReadQueryDto;
import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.domain.server.repository.ServerRepository;
import capstone.communityservice.domain.user.dto.UserReadResponseDto;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.exception.UserException;
import capstone.communityservice.domain.user.repository.UserRepository;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;
    private final ServerRepository serverRepository;
    private final DmRepository dmRepository;

    public User findUserByOriginalId(Long originalId) {
        return userRepository.findByOriginalId(originalId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "User Not Found"));
    }

    public List<User> findUsers(List<Long> userIds){
        return userRepository.findByOriginalIds(userIds);
    }


    public UserReadResponseDto read(Long userId) {
        // UserReadResponseDto.of(dmes, servers)
        List<ServerReadQueryDto> servers = serverRepository.findServersWithUserId(userId)
                .stream()
                .map(ServerReadQueryDto::of)
                .toList();

        List<DmReadQueryDto> dms = dmRepository.findDmsWithUserId(userId)
                .stream()
                .map(DmReadQueryDto::of)
                .toList();

        return UserReadResponseDto.of(servers, dms);
    }
}
