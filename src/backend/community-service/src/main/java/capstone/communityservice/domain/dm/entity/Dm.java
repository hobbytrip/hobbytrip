package capstone.communityservice.domain.dm.entity;

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
@SQLDelete(sql = "UPDATE dm SET deleted = true WHERE dm_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profile;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "dm")
    private List<DmUser> dmUsers = new ArrayList<>();

    public static Dm of(String name) {
        Dm dm = new Dm();
        dm.setName(name);

        return dm;
    }


    //===연관 관계 메서드===//
    public void addDmUser(DmUser dmUser){
        getDmUsers().add(dmUser);
        dmUser.setDm(this);
    }

    //===Setter 메서드===//

    public void setName(String name) {
        this.name = name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
