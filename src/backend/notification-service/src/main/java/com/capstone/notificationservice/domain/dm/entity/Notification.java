package com.capstone.notificationservice.domain.dm.entity;


import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.server.dto.MentionType;
import com.capstone.notificationservice.global.common.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    private Long dmRoomId;
    private Long serverId;

    private String content;

    private String url;

    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    private MentionType mentionType;

    @ManyToOne
    @JoinColumn(name= "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;
}
