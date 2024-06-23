package com.capstone.userservice.domain.user.service;


import com.capstone.userservice.domain.user.dto.request.TokenRequest;
import com.capstone.userservice.domain.user.dto.request.UserRequest;
import com.capstone.userservice.domain.user.exception.UserException;
import com.capstone.userservice.global.common.dto.TokenDto;
import com.capstone.userservice.global.common.entity.RefreshToken;
import com.capstone.userservice.global.exception.Code;
import com.capstone.userservice.global.respository.RefreshTokenRepository;
import com.capstone.userservice.global.util.TokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenUtil tokenUtil;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public TokenDto reissue(TokenRequest tokenRequest, UserRequest userRequest,
                            HttpServletResponse response) {
        // 1. Refersh Token 검증
        if (!tokenUtil.validateToken(tokenRequest.getRefreshToken())) {
            throw new UserException(Code.UNAUTHORIZED, "Refresh Token 이 유효하지 않습니다.");
        }

        //2. Access Token 에서 UserDetails 객체 가져오기
        Authentication authentication = tokenUtil.getAuthentication(tokenRequest.getAccessToken());

        //3. Access Token 에서 userEmail 가져오고, set
        String userEmail = tokenUtil.getEmail(tokenRequest.getAccessToken());
        userRequest.setEmail(userEmail);

        // 4. 저장소에서 User ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
                .orElseThrow(() -> new UserException(Code.UNAUTHORIZED, "로그아웃 된 사용자입니다."));

        // 5. Refresh Token 일치 검사
        if (!refreshToken.getValue().equals(tokenRequest.getRefreshToken())) {
            throw new UserException(Code.UNAUTHORIZED, "토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 6. 새로운 토큰 생성
        TokenDto tokenDto = tokenUtil.generateToken(userRequest, authentication);

        // 7. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());

        tokenUtil.setHeaderAccessToken(response, tokenDto.getAccessToken());

        refreshTokenRepository.save(newRefreshToken);

        // 8. 토큰 발급
        return tokenDto;
    }


    /**
     * @param accessToken
     * @return 로그인 되어 있으면 True, 로그아웃은 False
     */
    @Transactional
    public boolean isLogin(String accessToken) {
        try {
            // 1. Access Token 에서 UserDetails 객체 가져오기
            Long userId = tokenUtil.getUserId(accessToken);

            // 2. 저장소에서 UserId 를 기반으로 Refresh Token 값 가져옴
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(String.valueOf(userId));

            //객체가 값을 가지고 있으면 true
            if (refreshToken.isPresent()) {
                return true;
            }
        } catch (Exception e) {
            throw new UserException(Code.UNAUTHORIZED, "토큰의 정보가 유효하지 않습니다.");
        }
        return false;
    }

}
