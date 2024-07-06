package capstone.communityservice.domain.dm.entity;

import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@SQLDelete(sql = "UPDATE dm_user SET deleted = true WHERE dm_user_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dm_user")
public class DmUser extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dm_id", nullable = false)
    private Dm dm;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    public static DmUser of(Dm dm, User user) {
        DmUser dmUser = new DmUser();

        user.addDmUser(dmUser);
        dm.addDmUser(dmUser);

        return dmUser;
    }

    //===Setter 메서드===//
    public void setDm(Dm dm) {
        this.dm = dm;
    }

    public void setUser(User user){
        this.user = user;
    }


}
