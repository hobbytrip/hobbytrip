package com.capstone.notificationservice.domain.dm.dto.response;


import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.dm.entity.Notification;
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
    private List<Long> receiverIds;
    private String content;
    private AlarmType alarmType;
    private Boolean isRead;

    public static DmNotificationResponse from(Notification notification,  List<Long> receiverIds) {
        return DmNotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .dmRoomId(notification.getDmRoomId())
                .userId(notification.getReceiver().getUserId())
                .receiverIds(receiverIds)
                .content(notification.getContent())
                .alarmType(notification.getAlarmType())
                .isRead(false)
                .build();
    }
}
