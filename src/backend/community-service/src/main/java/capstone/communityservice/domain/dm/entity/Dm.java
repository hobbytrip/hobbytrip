package capstone.communityservice.domain.dm.entity;

import capstone.communityservice.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@SQLDelete(sql = "UPDATE dm SET deleted = true WHERE dm_id = ?")
@Where(clause = "deleted = false")
public class Dm extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;
}
