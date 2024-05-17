package com.capstone.notificationservice.domain.dm.dto.response;


import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.entity.Notification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DmNotificationResponse {
    Long notificationId;
    Long dmRoomId;
    Long userId;
    String content;
    AlarmType alarmType;
    Boolean isRead;

    public static DmNotificationResponse from(Notification notification) {
        return DmNotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .dmRoomId(notification.getDmRoomId())
                .userId(notification.getReceiver().getUserId())
                .content(notification.getContent())
                .alarmType(notification.getAlarmType())
                .isRead(false)
                .build();
    }
}
