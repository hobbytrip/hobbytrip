package capstone.communityservice.domain.dm.controller;

import capstone.communityservice.domain.dm.dto.DmCreateRequestDto;
import capstone.communityservice.domain.dm.dto.DmDeleteRequestDto;
import capstone.communityservice.domain.dm.dto.DmResponseDto;
import capstone.communityservice.domain.dm.dto.DmUpdateRequestDto;
import capstone.communityservice.domain.dm.service.DmCommandService;
import capstone.communityservice.domain.server.dto.ServerJoinRequestDto;
import capstone.communityservice.domain.server.dto.ServerResponseDto;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dm")
public class DmCommandController {

    private final DmCommandService dmCommandService;

    @PostMapping
    public DataResponseDto<Object> create(@Valid @RequestBody DmCreateRequestDto requestDto){
        DmResponseDto response = dmCommandService.save(requestDto);
        return DataResponseDto.of(response);
    }

//    @PostMapping("/join")
//    public DataResponseDto<Object> join(@Valid @RequestBody ServerJoinRequestDto requestDto){
//        ServerResponseDto response = serverCommandService.join(requestDto);
//        return DataResponseDto.of(response);
//    }

    @PatchMapping
    public DataResponseDto<Object> update(@Valid @RequestBody DmUpdateRequestDto requestDto){
        DmResponseDto response = dmCommandService.update(requestDto);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody DmDeleteRequestDto requestDto){
        dmCommandService.delete(requestDto);

        return DataResponseDto.of("Dm delete success!!");
    }
}
