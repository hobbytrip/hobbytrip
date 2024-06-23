package capstone.communityservice.domain.channel.controller;


import capstone.communityservice.domain.channel.service.ChannelQueryService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import capstone.communityservice.global.common.dto.SliceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelQueryController {

    private final ChannelQueryService channelQueryService;

    @GetMapping("/{channelId}/{userId}")
    public DataResponseDto read(@PathVariable("channelId") Long channelId,
                     @PathVariable("userId") Long userId,
                     @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                     @RequestParam(required = false, value = "title") String title){
        SliceResponseDto response = channelQueryService.read(channelId, userId, pageNo, title);

        return DataResponseDto.of(response);
    }
}
