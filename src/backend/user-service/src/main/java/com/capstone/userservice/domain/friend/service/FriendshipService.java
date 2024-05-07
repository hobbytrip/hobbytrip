package com.capstone.userservice.domain.friend.service;


import com.capstone.userservice.domain.friend.dto.response.WaitingFriendListResponse;
import com.capstone.userservice.domain.friend.entity.Friendship;
import com.capstone.userservice.domain.friend.entity.FriendshipStatus;
import com.capstone.userservice.domain.friend.exception.FriendException;
import com.capstone.userservice.domain.friend.repository.FriendshipRepository;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.domain.user.repository.UserRepository;
import com.capstone.userservice.global.common.dto.DataResponseDto;
import com.capstone.userservice.global.common.dto.TokenDto;
import com.capstone.userservice.global.exception.Code;
import com.capstone.userservice.global.util.TokenUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final TokenUtil tokenUtil;

    @Transactional
    public void createFriendship(TokenDto token, String toEmail) {
        //현재 로그인 되어 있는 사람 확인
        String fromEmail = tokenUtil.getEmail(token.getAccessToken());

        User fromUser = userRepository.findByEmail(fromEmail).orElseThrow(() ->
                new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));
        User toUSer = userRepository.findByEmail(toEmail).orElseThrow(() ->
                new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));

        //받는 사람 측 저장되는 친구 요청
        Friendship friendshipFrom = Friendship.builder()
                .users(fromUser)
                .userEmail(fromEmail)
                .friendEmail(toEmail)
                .status(FriendshipStatus.WAITING)
                .isFrom(true)
                .build();

        //보내는 사람 쪽에 저장되는 친구 요청
        Friendship friendshipTo = Friendship.builder()
                .users(toUSer)
                .userEmail(toEmail)
                .friendEmail(fromEmail)
                .status(FriendshipStatus.WAITING)
                .isFrom(false)
                .build();

        //각 유저 리스트에 저장
        fromUser.getFriendshipList().add(friendshipTo);
        toUSer.getFriendshipList().add(friendshipFrom);

        //친구 요청 번호 생성 후 저장
        friendshipRepository.save(friendshipTo);
        friendshipRepository.save(friendshipFrom);

        //매칭되는 친구요청 아이디 서로 저장
        friendshipTo.setCounterpartId(friendshipFrom.getId());
        friendshipFrom.setCounterpartId(friendshipTo.getId());
    }

    @Transactional
    public DataResponseDto<Object> getWaitingFriendList(TokenDto token) {
        String Email = tokenUtil.getEmail(token.getAccessToken());

        User user = userRepository.findByEmail(Email).orElseThrow(()
                -> new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));
        List<Friendship> friendshipList = user.getFriendshipList();
        List<WaitingFriendListResponse> result = new ArrayList<>();

        for (Friendship x : friendshipList) {
            if (!x.isFrom() && x.getStatus() == FriendshipStatus.WAITING) {
                User friend = userRepository.findByEmail(x.getFriendEmail()).orElseThrow(()
                        -> new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));
                WaitingFriendListResponse response = WaitingFriendListResponse.builder()
                        .friendshipId(x.getId())
                        .friendName(friend.getUsername())
                        .status(x.getStatus())
                        .imageUrl(friend.getProfileImage())
                        .build();
                result.add(response);
            }
        }

        return DataResponseDto.of(result);
    }
}
