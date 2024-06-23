package com.capstone.notificationservice.domain.dm.dto.response;


import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.dto.DmNotificationDto;
import com.capstone.notificationservice.domain.dm.entity.DmNotification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DmNotificationResponse extends DmNotificationDto {
    private Long notificationId;
    private Long dmRoomId;
    private Long userId;
    private Long receiverId;
    private String content;
    private AlarmType alarmType;
    private Boolean isRead;

    public static DmNotificationResponse from(DmNotification dmNotification, Long userId) {
        return DmNotificationResponse.builder()
                .notificationId(dmNotification.getNotificationId())
                .dmRoomId(dmNotification.getDmRoomId())
                .userId(userId)
                .receiverId(dmNotification.getReceiver().getUserId())
                .content(dmNotification.getContent())
                .alarmType(dmNotification.getAlarmType())
                .isRead(false)
                .build();
    }
}
