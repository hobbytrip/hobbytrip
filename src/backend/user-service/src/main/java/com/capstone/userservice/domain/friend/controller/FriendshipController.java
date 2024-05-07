package com.capstone.userservice.domain.friend.controller;

import com.capstone.userservice.domain.friend.service.FriendshipService;
import com.capstone.userservice.global.common.dto.DataResponseDto;
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
    private final String Header = "Authorization";

    @PostMapping("{email}")
    public DataResponseDto<Object> sendFriendshipRequest(@Valid @PathVariable("email") String email,
                                                         HttpServletRequest request) {
        String token = request.getHeader(Header);
        return DataResponseDto.of(friendshipService.createFriendship(token, email));
    }

    @PostMapping("/approve/{friendshipId}")
    public DataResponseDto<Object> approveFriendship(@Valid @PathVariable("friendshipId") Long friendshipId) {
        return DataResponseDto.of(friendshipService.approveFriendship(friendshipId));
    }

    @GetMapping("/received")
    public DataResponseDto<Object> getWaitingFriendInfo(HttpServletRequest request) {
        String token = request.getHeader(Header);
        return DataResponseDto.of(friendshipService.getWaitingFriendList(token));
    }

    @PostMapping("/delete/{friendshipId}")
    public DataResponseDto<Object> deleteFriendship(@Valid @PathVariable("friendshipId") Long friendshipId) {
        return DataResponseDto.of(friendshipService.deleteFriendship(friendshipId));
    }
}

