package com.capstone.notificationservice.domain.server.dto;

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
    private String type;
    private String content;
    private String url;
}
