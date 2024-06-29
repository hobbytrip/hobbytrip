package capstone.communityservice.domain.dm.controller;

import capstone.communityservice.domain.dm.dto.request.*;
import capstone.communityservice.domain.dm.dto.response.DmResponse;
import capstone.communityservice.domain.dm.service.DmCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dm")
public class DmCommandController {

    private final DmCommandService dmCommandService;

    @PostMapping
    public DataResponseDto<Object> create(@Valid @RequestBody DmCreateRequest requestDto){
        DmResponse response = dmCommandService.create(requestDto);
        return DataResponseDto.of(response);
    }

    /**
     * 기존 DM에 있던 사람인지 검사를 할 때의 처리 고민.
     */
    @PostMapping("/join")
    public DataResponseDto<Object> join(@Valid @RequestBody DmJoinRequest requestDto){
        DmResponse response = dmCommandService.join(requestDto);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(@Valid @RequestBody DmUpdateRequest requestDto){
        DmResponse response = dmCommandService.update(requestDto);

        return DataResponseDto.of(response);
    }

    @PatchMapping("/profile")
    public DataResponseDto<Object> updateProfile(
            @Valid @RequestPart(value = "requestDto") DmUpdateProfileRequest requestDto,
            @RequestPart(name = "profile") MultipartFile profile
    ) {
        DmResponse response = dmCommandService.updateProfile(requestDto, profile);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody DmDeleteRequest requestDto){
        dmCommandService.delete(requestDto);

        return DataResponseDto.of("Dm delete success!!");
    }

    @DeleteMapping("/profile")
    public DataResponseDto<Object> delete(@Valid @RequestBody DmDeleteProfileRequest requestDto){
        dmCommandService.deleteProfile(requestDto);


        return DataResponseDto.of("Dm profile delete success!!");
    }
}
