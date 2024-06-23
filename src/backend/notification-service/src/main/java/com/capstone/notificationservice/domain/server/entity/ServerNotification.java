package com.capstone.notificationservice.domain.server.entity;

import com.capstone.notificationservice.domain.common.AlarmType;
import com.capstone.notificationservice.domain.server.dto.MentionType;
import com.capstone.notificationservice.domain.user.entity.User;
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
public class ServerNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "server_id")
    private Long serverId;
    @Column(name = "content")
    private String content;
    @Column(name = "is_read")
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type")
    private AlarmType alarmType;

    @Enumerated(EnumType.STRING)
    @Column(name = "mention_type")
    private MentionType mentionType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
