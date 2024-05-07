package com.capstone.userservice.domain.user.dto.request;


import com.capstone.userservice.domain.user.entity.Authority;
import com.capstone.userservice.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long userId;
    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotBlank
    private String username;
    private Date birthdate;
    private Date createdAt;
    @NotNull
    private String password;
    private Boolean notificationEnabled;

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
