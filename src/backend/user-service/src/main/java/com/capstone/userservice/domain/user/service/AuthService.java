package com.capstone.userservice.domain.user.service;


import com.capstone.userservice.domain.user.dto.TokenRequestDto;
import com.capstone.userservice.domain.user.dto.UserRequestDto;
import com.capstone.userservice.domain.user.dto.UserResponseDto;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.domain.user.repository.UserRepository;
import com.capstone.userservice.global.common.dto.JwtTokenDto;
import com.capstone.userservice.global.entity.RefreshToken;
import com.capstone.userservice.global.respository.RefreshTokenRepository;
import com.capstone.userservice.global.util.TokenUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtil tokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        User user = userRequestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public JwtTokenDto login(UserRequestDto userRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = userRequestDto.toAuthentication();

        // 2. 실제 검증 (비밀번호 체크)
        //authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtTokenDto tokenDto = tokenUtil.generateToken(userRequestDto, authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public JwtTokenDto reissue(TokenRequestDto tokenRequestDto, UserRequestDto userRequestDto) {
        // 1. Refersh Token 검증
        if (!tokenUtil.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 User Email 가져오기
        Authentication authentication = tokenUtil.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 UserId 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        JwtTokenDto tokenDto = tokenUtil.generateToken(userRequestDto, authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    @Transactional
    public boolean checkLoginToken(TokenRequestDto tokenRequestDto) {

        try {
            // 1. Refersh Token 검증
            if (!tokenUtil.validateToken(tokenRequestDto.getRefreshToken())) {
                throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
            }

            // 2. Access Token 에서 User Email 가져오기
            Authentication authentication = tokenUtil.getAuthentication(tokenRequestDto.getAccessToken());

            // 3. 저장소에서 UserId 를 기반으로 Refresh Token 값 가져옴
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByKey(authentication.getName());

            if (refreshToken.isPresent()) {
                return tokenUtil.validateToken(refreshToken.get().getValue());
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
