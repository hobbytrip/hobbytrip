package com.capstone.userservice.domain.friend.controller;

import com.capstone.userservice.domain.friend.service.FriendshipService;
import com.capstone.userservice.global.common.dto.DataResponseDto;
import com.capstone.userservice.global.config.kafka.dto.FriendAlarmEventDto;
import com.capstone.userservice.global.config.kafka.producer.FriendAlarmEventProducer;
import com.capstone.userservice.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final TokenUtil tokenUtil;
    private final FriendAlarmEventProducer friendAlarmEventProducer;
    private final String Header = "Authorization";

    @PostMapping("{email}")
    public DataResponseDto<Object> sendFriendshipRequest(@Valid @PathVariable("email") String email,
                                                         HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        FriendAlarmEventDto friendAlarmEventDto = new FriendAlarmEventDto(tokenUtil.getUserId(temp), email);
        friendAlarmEventProducer.sendToFriendAlarmEventTopic(friendAlarmEventDto);

        return DataResponseDto.of(friendshipService.createFriendship(temp, email));
    }

    @PostMapping("/approve/{friendshipId}")
    public DataResponseDto<Object> approveFriendship(@Valid @PathVariable("friendshipId") Long friendshipId) {
        return DataResponseDto.of(friendshipService.approveFriendship(friendshipId));
    }

    @GetMapping("/friendList")
    public DataResponseDto<Object> getFriendList(HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        return DataResponseDto.of(friendshipService.getFriendList(temp));
    }

    @GetMapping("/received")
    public DataResponseDto<Object> getWaitingFriendInfo(HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        return DataResponseDto.of(friendshipService.getWaitingFriendList(temp));
    }

    @PostMapping("/delete/{friendshipId}")
    public DataResponseDto<Object> deleteFriendship(@Valid @PathVariable("friendshipId") Long friendshipId) {
        return DataResponseDto.of(friendshipService.deleteFriendship(friendshipId));
    }

    @GetMapping("/checkFriendship")
    public DataResponseDto<Object> checkFriendship(HttpServletRequest request) {
        String token = request.getHeader(Header);
        String temp = trimToken(token);

        return DataResponseDto.of(friendshipService.checkFriendship(temp));
    }

    public String trimToken(String token) {
        return token.split(" ")[1].trim();
    }
}

