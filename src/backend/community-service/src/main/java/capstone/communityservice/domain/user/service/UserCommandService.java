package capstone.communityservice.domain.user.service;

import capstone.communityservice.domain.user.dto.UserRequestIdDto;
import capstone.communityservice.domain.user.dto.UserResponseDto;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.repository.UserRepository;
import capstone.communityservice.global.external.UserServiceFakeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
//    private final UserServiceClient userServiceClient;
    private final UserQueryService userQueryService;

    private final UserServiceFakeClient userServiceFakeClient;


    public UserResponseDto save(UserRequestIdDto requestDto) {
        Optional<User> user = userRepository.findByOriginalId(requestDto.getOriginalId());

        if(user.isPresent()){
            return UserResponseDto.of(user.get());
        } else{
            // User newUser = User.of(userServiceClient.getUser(requestDto.getOriginalId()));
            User newUser = User.of(userServiceFakeClient.getUser());
            userRepository.save(newUser);
            return UserResponseDto.of(newUser);
        }
    }
}
