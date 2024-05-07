package com.capstone.userservice.domain.friend.service;


import com.capstone.userservice.domain.friend.dto.response.WaitingFriendListResponse;
import com.capstone.userservice.domain.friend.entity.Friendship;
import com.capstone.userservice.domain.friend.entity.FriendshipStatus;
import com.capstone.userservice.domain.friend.exception.FriendException;
import com.capstone.userservice.domain.friend.repository.FriendshipRepository;
import com.capstone.userservice.domain.user.entity.User;
import com.capstone.userservice.domain.user.repository.UserRepository;
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
    public Boolean createFriendship(String token, String toEmail) {
        try {
            //현재 로그인 되어 있는 사람 확인
            String fromEmail = tokenUtil.getEmail(token);

            User fromUser = userRepository.findByEmail(fromEmail).orElseThrow(() ->
                    new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));
            User toUSer = userRepository.findByEmail(toEmail).orElseThrow(() ->
                    new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));

            //받는 사람 측 저장되는 친구 요청
            Friendship friendshipFrom = Friendship.builder()
                    .user(fromUser)
                    .userEmail(fromEmail)
                    .friendEmail(toEmail)
                    .status(FriendshipStatus.WAITING)
                    .isFrom(true) //받은 사람
                    .build();

            //보내는 사람 쪽에 저장되는 친구 요청
            Friendship friendshipTo = Friendship.builder()
                    .user(toUSer)
                    .userEmail(toEmail)
                    .friendEmail(fromEmail)
                    .status(FriendshipStatus.WAITING)
                    .isFrom(false) //보낸 사람
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

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public List<WaitingFriendListResponse> getWaitingFriendList(String token) {
        Long userId = tokenUtil.getUserId(token);

        User user = userRepository.findById(userId).orElseThrow(()
                -> new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));
        List<Friendship> friendshipList = user.getFriendshipList();

        List<WaitingFriendListResponse> result = new ArrayList<>();

        for (Friendship x : friendshipList) {
            //요청을 받았으면서 수락 대기 중인 요청만 조회
            if (x.isFrom() && x.getStatus() == FriendshipStatus.WAITING) {
                User friend = userRepository.findByEmail(x.getFriendEmail()).orElseThrow(()
                        -> new FriendException(Code.NOT_FOUND, "회원 조회를 실패했습니다."));
                WaitingFriendListResponse response = WaitingFriendListResponse.builder()
                        .friendshipId(x.getId())
                        .friendEmail(friend.getEmail())
                        .status(x.getStatus())
                        .imageUrl(friend.getProfileImage())
                        .build();
                result.add(response);
            }
        }
        return result;
    }

    @Transactional
    public String approveFriendship(Long friendshipId) {
        // 누른 친구 요청과 매칭되는 상대방 친구 요청을 둘 다 가져온다.
        Friendship friendship = friendshipRepository.findById(friendshipId).orElseThrow(() ->
                new FriendException(Code.NOT_FOUND, "친구 요청 조회를 실패했습니다."));
        Friendship counterFriendship = friendshipRepository.findById(friendship.getCounterpartId())
                .orElseThrow(() ->
                        new FriendException(Code.NOT_FOUND, "친구 요청 조회를 실패했습니다."));

        //둘다 상태를 ACCEPT로 변경
        friendship.acceptFriendshipRequest();
        counterFriendship.acceptFriendshipRequest();

        return "ok";
    }
}
