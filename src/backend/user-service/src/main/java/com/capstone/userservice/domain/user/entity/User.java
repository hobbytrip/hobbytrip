package com.capstone.userservice.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(length = 11)
    private String phone;

    private String statusMessage;
    private String profileImage;
    private Date birthdate;
    private boolean notificationEnabled;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date modifiedAt;

    @Builder
    public User(String email,
                String username,
                String nickname,
                String password) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}
