package com.capstone.notificationservice.domain.server.dto;

import com.capstone.notificationservice.domain.common.AlarmType;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ServerNotificationDto {
    private Long userId;
    private Long serverId;
    private String writer;
    private String content;
    private String profileImage;
    private List<Long> receiverIds;
    private AlarmType alarmType;
    private MentionType mentionType;

}
