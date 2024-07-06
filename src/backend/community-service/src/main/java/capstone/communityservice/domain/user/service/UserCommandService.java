package capstone.communityservice.domain.user.service;

import capstone.communityservice.domain.user.dto.request.UserCreateRequest;
import capstone.communityservice.domain.user.dto.response.UserResponse;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.repository.UserRepository;
import capstone.communityservice.global.external.UserServiceClient;
import capstone.communityservice.global.external.UserServiceFakeClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;
    private final UserServiceClient userServiceClient;
    private final UserQueryService userQueryService;

    private final UserServiceFakeClient userServiceFakeClient;


    public UserResponse save(UserCreateRequest request) {
        return UserResponse.of(
                userRepository.findByOriginalId(
                                request.getOriginalId())
                        .orElseGet(() -> {
                             User newUser = User.of(
                                     userServiceClient.getUser(
                                             request.getOriginalId()
                                     )
                             );
//                            User newUser = User.of(userServiceFakeClient.getUser(request.getOriginalId()));
                            userRepository.save(newUser);
                            return newUser;
                        }));


    }
}