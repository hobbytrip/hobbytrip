package com.capstone.userservice.domain.user.service;


import com.capstone.userservice.domain.user.dto.request.TokenRequest;
import com.capstone.userservice.domain.user.dto.request.UserDeleteRequest;
import com.capstone.userservice.domain.user.dto.request.UserRequest;
import com.capstone.userservice.domain.user.dto.response.UserResponse;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.domain.user.exception.UserException;
import com.capstone.userservice.domain.user.repository.UserRepository;
import com.capstone.userservice.global.common.dto.TokenDto;
import com.capstone.userservice.global.common.entity.RefreshToken;
import com.capstone.userservice.global.exception.Code;
import com.capstone.userservice.global.respository.RefreshTokenRepository;
import com.capstone.userservice.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UserResponse signup(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new UserException(Code.BAD_REQUEST, "이미 가입되어 있는 유저입니다.");
        }

        User user = userRequest.toUser(passwordEncoder);
        return UserResponse.from(userRepository.save(user));
    }

    @Transactional
    public TokenDto login(UserRequest userRequest, HttpServletResponse response) {
        try {
            // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = userRequest.toAuthentication();

            // 2. 실제 검증 (비밀번호 체크)
            //authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenUtil.generateToken(userRequest, authentication);

            // 4. RefreshToken 저장
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();

            tokenUtil.setHeaderAccessToken(response, tokenDto.getAccessToken());

            refreshTokenRepository.save(refreshToken);

            // 5. 토큰 발급
            return tokenDto;
        } catch (Exception e) {
            throw new UserException(Code.UNAUTHORIZED, "로그인 실패: 인증 정보가 유효하지 않습니다.");
        }
    }

    @Transactional
    public boolean logout(TokenRequest tokenRequest) {
        // 1. Access Token 유효성 검사
        if (!tokenUtil.validateToken(tokenRequest.getAccessToken())) {
            throw new UserException(Code.INTERNAL_ERROR, "Access Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 UserDetails 객체 가져오기
        Authentication authentication = tokenUtil.getAuthentication(tokenRequest.getAccessToken());

        // 3. 저장소에서 해당 User의 Refresh Token 가져오기
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "로그아웃 하려는 사용자의 Refresh Token이 존재하지 않습니다."));

        // 4. 제출된 Refresh Token 일치 검사
        if (!refreshToken.getValue().equals(tokenRequest.getRefreshToken())) {
            throw new UserException(Code.BAD_REQUEST, "제출된 Refresh Token이 저장된 값과 일치하지 않습니다.");
        }

        // 5. Refresh Token 삭제
        refreshTokenRepository.delete(refreshToken.getUserID());

        return true;
    }

    @Transactional
    public boolean deleteUser(UserDeleteRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(Code.NOT_FOUND, "이메일이 존재하지 않습니다."));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            userRepository.delete(user);
            return true;
        } else {
            throw new UserException(Code.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
    }
}
