package capstone.communityservice.domain.channel.controller;


import capstone.communityservice.domain.channel.service.ChannelQueryService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelQueryController {

    private final ChannelQueryService channelQueryService;

    @GetMapping("/{channelId}/{userId}")
    public void sendUserLocation(@PathVariable("channelId") Long channelId, @PathVariable("userId") Long userId){
        channelQueryService.sendUserLocation(channelId, userId);
    }
}
