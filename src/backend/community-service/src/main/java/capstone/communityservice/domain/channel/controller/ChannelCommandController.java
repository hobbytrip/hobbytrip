package capstone.communityservice.domain.channel.controller;


import capstone.communityservice.domain.channel.dto.request.ChannelCreateRequest;
import capstone.communityservice.domain.channel.dto.request.ChannelDeleteRequest;
import capstone.communityservice.domain.channel.dto.response.ChannelResponse;
import capstone.communityservice.domain.channel.dto.request.ChannelUpdateRequest;
import capstone.communityservice.domain.channel.service.ChannelCommandService;
import capstone.communityservice.global.common.dto.DataResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelCommandController {
    private final ChannelCommandService channelCommandService;

    @PostMapping
    public DataResponseDto<Object> create(@Valid @RequestBody ChannelCreateRequest request){
        ChannelResponse response = channelCommandService.create(request);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(@Valid @RequestBody ChannelUpdateRequest request){
        ChannelResponse response = channelCommandService.update(request);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody ChannelDeleteRequest request){
        channelCommandService.delete(request);

        return DataResponseDto.of("Channel delete success!");
    }
}
