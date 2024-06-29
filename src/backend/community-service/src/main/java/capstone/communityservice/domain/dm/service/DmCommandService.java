package capstone.communityservice.domain.dm.service;

import capstone.communityservice.domain.dm.dto.request.*;
import capstone.communityservice.domain.dm.dto.response.DmResponse;
import capstone.communityservice.domain.dm.entity.Dm;
import capstone.communityservice.domain.dm.entity.DmUser;
import capstone.communityservice.domain.dm.exception.DmException;
import capstone.communityservice.domain.dm.repository.DmRepository;
import capstone.communityservice.domain.dm.repository.DmUserRepository;
import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.domain.user.service.UserQueryService;
import capstone.communityservice.global.common.dto.kafka.CommunityDmEventDto;
import capstone.communityservice.global.common.service.FileUploadService;
import capstone.communityservice.global.exception.Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DmCommandService {
    private static final String dmKafkaTopic = "communityDmEventTopic";

    private final KafkaTemplate<String, CommunityDmEventDto> dmKafkaTemplate;

    private final DmRepository dmRepository;
    private final DmUserRepository dmUserRepository;
    private final UserQueryService userQueryService;
    private final FileUploadService fileUploadService;

    public DmResponse create(DmCreateRequest request) {
        List<User> users = findUsers(request.getUserIds());
        String dmName = createDmName(users);

        Dm newDm = dmRepository.save(Dm.of(dmName));

        saveDmUser(users, newDm);

        return DmResponse.of(newDm);
    }


    public DmResponse join(DmJoinRequest request) {
        List<User> users = findUsers(request.getUserIds());
        String dmName = createDmName(users);

        Dm findDm = validateDm(request.getDmId());

        findDm.setName(findDm.getName() + ", " + dmName);

        saveDmUser(users, findDm);

        return DmResponse.of(findDm);
    }

    public DmResponse update(DmUpdateRequest request) {
        Dm findDm = validateDm(request.getDmId());
        findDm.setName(request.getName());

        dmKafkaTemplate.send(dmKafkaTopic, CommunityDmEventDto.of("dm-update", findDm));

        printKafkaLog("update");

        return DmResponse.of(findDm);
    }

    public DmResponse updateProfile(DmUpdateProfileRequest request, MultipartFile profile) {
        Dm findDm = validateDm(request.getDmId());

        String profileUrl = fileUploadService.save(profile);
//        String profileUrl = "http://image.png";

        findDm.setProfile(profileUrl);

        dmKafkaTemplate.send(dmKafkaTopic, CommunityDmEventDto.of("dm-update", findDm));

        printKafkaLog("update");

        return DmResponse.of(findDm);
    }

    public void delete(DmDeleteRequest request) {
        Dm findDm = validateDm(request.getDmId());

        validateDmProfileDelete(findDm);
        dmUserRepository.deleteAllByDmId(findDm.getId());

        dmRepository.delete(findDm);
    }


    public void deleteProfile(DmDeleteProfileRequest request) {
        Dm findDm = validateDm(request.getDmId());

        if(findDm.getProfile().equals(request.getProfile())) {
            findDm.setProfile(null);
        } else{
            throw new DmException(Code.VALIDATION_ERROR, "Dm profileUrl not equals.");
        }
    }

    private void validateDmProfileDelete(Dm dm) {
        if(dm.getProfile() != null){
            fileUploadService.delete(dm.getProfile());
        }
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

    private void printKafkaLog(String type) {
        log.info("Kafka event send about Dm {}", type);
    }
}
