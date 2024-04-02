package com.capstone.userservice.domain.user.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;
    private String email;
    private String password;

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
