package capstone.communityservice.domain.dm.entity;

import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@SQLDelete(sql = "UPDATE dm_user SET deleted = true WHERE dm_user_id = ?")
@Where(clause = "deleted = false")
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
}
