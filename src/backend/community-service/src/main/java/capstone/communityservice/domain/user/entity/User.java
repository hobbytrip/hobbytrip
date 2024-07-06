package capstone.communityservice.domain.user.entity;

import capstone.communityservice.domain.dm.entity.DmUser;
import capstone.communityservice.domain.server.entity.ServerUser;
import capstone.communityservice.domain.user.dto.response.UserFeignResponseDto;
import capstone.communityservice.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private Long originalId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private String profile;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "user")
    private List<ServerUser> serverUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<DmUser> dmUsers = new ArrayList<>();

    //===연관 관계 메서드===//
    public void addServerUser(ServerUser serverUser){
        getServerUsers().add(serverUser);
        serverUser.setUser(this);
    }

    public void addDmUser(DmUser dmUser) {
        getDmUsers().add(dmUser);
        dmUser.setUser(this);
    }

    //===생성 메서드===//
    public static User of(UserFeignResponseDto userFeignResponseDto){
        User user = new User();

        user.setOriginalId(userFeignResponseDto.getOriginalId());
        user.setEmail(userFeignResponseDto.getEmail());
        user.setName(userFeignResponseDto.getName());
        user.setProfile(userFeignResponseDto.getProfile());

        return user;
    }

    //===Setter 메서드===//
    private void setEmail(String email){
        this.email = email;
    }

    private void setProfile(String profile){
        this.profile = profile;
    }

    private void setName(String name){
        this.name = name;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }
}
