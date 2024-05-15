package com.capstone.notificationservice.domain.dm.dto.request;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DmNotificationRequest {
    private Long notificationId;
    private Long userId;
    private String content;
    private String url;
}
