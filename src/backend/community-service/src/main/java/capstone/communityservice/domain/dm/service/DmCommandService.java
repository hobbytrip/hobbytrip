package capstone.communityservice.domain.dm.service;

import capstone.communityservice.domain.dm.dto.*;
import capstone.communityservice.domain.dm.entity.Dm;
import capstone.communityservice.domain.dm.entity.DmUser;
import capstone.communityservice.domain.dm.exception.DmException;
import capstone.communityservice.domain.dm.repository.DmRepository;
import capstone.communityservice.domain.dm.repository.DmUserRepository;
import capstone.communityservice.domain.server.dto.ServerResponseDto;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DmCommandService {

    private final DmRepository dmRepository;
    private final DmUserRepository dmUserRepository;
    private final UserQueryService userQueryService;

    public DmResponseDto save(DmCreateRequestDto requestDto) {
        List<User> users = findUsers(requestDto.getUserIds());
        String dmName = createDmName(users);

        Dm newDm = dmRepository.save(Dm.of(dmName));

        saveDmUser(users, newDm);

        return DmResponseDto.of(newDm);
    }

    public DmResponseDto join(DmJoinRequestDto requestDto) {
        List<User> users = findUsers(requestDto.getUserIds());
        String dmName = createDmName(users);

        Dm findDm = validateDm(requestDto.getDmId());

        findDm.setName(findDm.getName() + ", " + dmName);

        saveDmUser(users, findDm);

        return DmResponseDto.of(findDm);
    }

    public DmResponseDto update(DmUpdateRequestDto requestDto) {
        Dm findDm = validateDm(requestDto.getDmId());
        findDm.setName(requestDto.getName());

        return DmResponseDto.of(findDm);
    }



    private void saveDmUser(List<User> users, Dm dm) {
        List<DmUser> dmUsers = users.stream()
                .map(user -> DmUser.of(dm, user))
                .collect(Collectors.toList());

        dmUserRepository.saveAll(dmUsers);
    }

    private String createDmName(List<User> users) {
        return users.stream()
                .map(User::getName)
                .collect(Collectors.joining(", "));
    }


    private List<User> findUsers(List<Long> userIds) {
        return userQueryService.findUsers(userIds);
    }


    private Dm validateDm(Long dmId){
        return dmRepository.findById(dmId)
                .orElseThrow(() -> new DmException(Code.NOT_FOUND, "Not Found Dm"));
    }

    public void delete(DmDeleteRequestDto requestDto) {
        Dm findDm = validateDm(requestDto.getDmId());

        dmRepository.delete(findDm);
    }


}
