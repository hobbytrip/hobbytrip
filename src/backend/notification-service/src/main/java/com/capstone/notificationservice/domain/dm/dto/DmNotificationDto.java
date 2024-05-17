package com.capstone.notificationservice.domain.dm.dto;


import com.capstone.notificationservice.domain.common.AlarmType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DmNotificationDto {
    private Long dmRoomId;
    private Long userId;
    private String content;
    private AlarmType alarmType;
}
