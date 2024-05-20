package com.capstone.notificationservice.domain.dm.dto.response;


import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.entity.DmNotification;
import java.util.List;
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
    private Long notificationId;
    private Long dmRoomId;
    private Long userId;
    private Long receiverId;
    private String content;
    private AlarmType alarmType;
    private Boolean isRead;

    public static DmNotificationResponse from(DmNotification dmNotification) {
        return DmNotificationResponse.builder()
                .notificationId(dmNotification.getNotificationId())
                .dmRoomId(dmNotification.getDmRoomId())
                .userId(dmNotification.getUserId())
                .receiverId(dmNotification.getReceiver().getUserId())
                .content(dmNotification.getContent())
                .alarmType(dmNotification.getAlarmType())
                .isRead(false)
                .build();
    }
}
