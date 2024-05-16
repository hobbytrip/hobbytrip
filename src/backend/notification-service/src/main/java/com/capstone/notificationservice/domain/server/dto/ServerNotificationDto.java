package com.capstone.notificationservice.domain.server.dto;

import com.capstone.notificationservice.domain.common.AlarmType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ServerNotificationDto {
    private Long notificationId;
    private Long userId;
    private Long serverId;
    private MentionType mentionType;
    private AlarmType alarmType;
    private String content;
}
