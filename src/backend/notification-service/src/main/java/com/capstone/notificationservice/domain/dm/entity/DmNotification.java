package com.capstone.notificationservice.domain.dm.entity;


import com.capstone.notificationservice.domain.common.AlarmType;
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
import jakarta.persistence.Table;
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
@Table(name = "dm_notification")
public class DmNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "dmroom_id")
    private Long dmRoomId;
    @Column(name="content")
    private String content;
    @Column(name="is_read")
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @ManyToOne
    @JoinColumn(name= "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User receiver;
}
