package com.capstone.userservice.global.config.kafka.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendAlarmEventDto {
    private Long userId;
    private String receiverEmail;
}
