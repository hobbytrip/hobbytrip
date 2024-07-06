package capstone.communityservice.domain.dm.controller;

import capstone.communityservice.domain.dm.dto.response.DmReadResponse;
import capstone.communityservice.domain.dm.service.DmQueryService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dm")
public class DmQueryController {
    private final DmQueryService dmQueryService;

    @GetMapping("/{dmId}")
    public DataResponseDto<Object> read(@PathVariable("dmId") Long dmId){
        DmReadResponse response = dmQueryService.read(dmId);

        return DataResponseDto.of(response);
    }
}
