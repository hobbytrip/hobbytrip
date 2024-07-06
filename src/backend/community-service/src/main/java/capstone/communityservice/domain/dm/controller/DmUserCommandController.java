package capstone.communityservice.domain.dm.controller;

import capstone.communityservice.domain.dm.dto.request.DmUserDeleteRequest;
import capstone.communityservice.domain.dm.service.DmUserCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dmUser")
public class DmUserCommandController {

    private final DmUserCommandService dmUserCommandService;

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody DmUserDeleteRequest requestDto){
        dmUserCommandService.delete(requestDto);

        return DataResponseDto.of("DmUser delete success!!");
    }
}
