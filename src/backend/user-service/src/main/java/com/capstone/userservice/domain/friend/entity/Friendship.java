package com.capstone.userservice.domain.friend.entity;


import com.capstone.userservice.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String userEmail;
    private String friendEmail;
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
    private boolean isFrom;

    private Long counterpartId;

    public void acceptFriendshipRequest() {
        status = FriendshipStatus.ACCEPT;
    }

    public void setCounterpartId(Long id) {
        counterpartId = id;
    }

}
