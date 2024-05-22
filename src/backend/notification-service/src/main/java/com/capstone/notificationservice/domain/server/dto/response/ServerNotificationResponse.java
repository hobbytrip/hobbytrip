package com.capstone.notificationservice.domain.server.dto.response;

import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.dto.response.DmNotificationResponse;
import com.capstone.notificationservice.domain.dm.entity.DmNotification;
import com.capstone.notificationservice.domain.server.dto.MentionType;
import com.capstone.notificationservice.domain.server.dto.ServerNotificationDto;
import com.capstone.notificationservice.domain.server.entity.ServerNotification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerNotificationResponse {
    private Long notificationId;
    private Long serverId;
    private Long userId;
    private Long receiverId;
    private String content;
    private MentionType mentionType;
    private AlarmType alarmType;
    private Boolean isRead;

    public static ServerNotificationResponse from(ServerNotification serverNotification, Long userId) {
        return ServerNotificationResponse.builder()
                .notificationId(serverNotification.getNotificationId())
                .serverId(serverNotification.getServerId())
                .userId(userId)
                .receiverId(serverNotification.getReceiver().getUserId())
                .content(serverNotification.getContent())
                .mentionType(serverNotification.getMentionType())
                .alarmType(serverNotification.getAlarmType())
                .isRead(false)
                .build();
    }
}
