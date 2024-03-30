package capstone.communityservice.domain.user.service;

import capstone.communityservice.domain.user.dto.UserRequestEmailDto;
import capstone.communityservice.domain.user.dto.UserResponseDto;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.exception.UserException;
import capstone.communityservice.domain.user.repository.UserRepository;
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
//    private final UserServiceClient userServiceClient;
    private final UserQueryService userQueryService;

    private final UserServiceFakeClient userServiceFakeClient;

    public UserResponseDto findOrSaveUser(UserRequestEmailDto requestDto){
        User user;
        try{
            user = userQueryService.findByEmail(requestDto.getEmail());
        } catch(UserException e){
            // user = User.of(userServiceClient.getUser(email));
            user = User.of(userServiceFakeClient.getUser());
            userRepository.save(user);
        }

        return UserResponseDto.of(user);
    }


}
