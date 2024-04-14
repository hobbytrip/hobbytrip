package capstone.communityservice.domain.channel.controller;


import capstone.communityservice.domain.channel.dto.ChannelCreateRequestDto;
import capstone.communityservice.domain.channel.dto.ChannelDeleteRequestDto;
import capstone.communityservice.domain.channel.dto.ChannelResponseDto;
import capstone.communityservice.domain.channel.dto.ChannelUpdateRequestDto;
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
    public DataResponseDto<Object> create(@Valid @RequestBody ChannelCreateRequestDto requestDto){
        ChannelResponseDto response = channelCommandService.create(requestDto);

        return DataResponseDto.of(response);
    }

    @PatchMapping
    public DataResponseDto<Object> update(@Valid @RequestBody ChannelUpdateRequestDto requestDto){
        ChannelResponseDto response = channelCommandService.update(requestDto);

        return DataResponseDto.of(response);
    }

    @DeleteMapping
    public DataResponseDto<Object> delete(@Valid @RequestBody ChannelDeleteRequestDto requestDto){
        channelCommandService.delete(requestDto);

        return DataResponseDto.of("Channel delete success!");
    }
}
