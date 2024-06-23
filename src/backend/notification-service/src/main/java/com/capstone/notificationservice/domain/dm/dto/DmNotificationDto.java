package com.capstone.notificationservice.domain.dm.dto;


import com.capstone.notificationservice.domain.common.AlarmType;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DmNotificationDto {
    private Long userId;
    private Long dmRoomId;
    private String content;
    private String writer;
    private String profileImage;
    private AlarmType alarmType;
    private List<Long> receiverIds;
}
