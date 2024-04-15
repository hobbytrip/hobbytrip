package com.capstone.userservice.domain.profile.controller;


import com.capstone.userservice.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

//    @GetMapping("/profile")
//    public ResponseEntity<ProfileResponseDto> read(ProfileRequestDto requestDto) {
//        return ResponseEntity.ok(profileService.read(requestDto));
//    }

}
