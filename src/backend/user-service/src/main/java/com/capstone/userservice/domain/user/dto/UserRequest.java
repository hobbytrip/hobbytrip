package com.capstone.userservice.domain.user.dto;


import com.capstone.userservice.domain.user.entity.Authority;
import com.capstone.userservice.domain.user.entity.User;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    private Long userId;
    private String email;
    private String nickname;
    private String username;
    private Date birthdate;
    private Date createdAt;
    private String password;
    private boolean notificationEnabled;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .birthdate(birthdate)
                .notificationEnabled(notificationEnabled)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
