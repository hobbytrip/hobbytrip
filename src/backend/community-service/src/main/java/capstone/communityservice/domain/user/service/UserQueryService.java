package capstone.communityservice.domain.user.service;

import capstone.communityservice.domain.user.dto.UserResponseDto;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.exception.UserException;
import capstone.communityservice.domain.user.repository.UserRepository;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User findUserByOriginalId(Long originalId) {
        return userRepository.findByOriginalId(originalId)
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "User Not Found"));
    }
}
