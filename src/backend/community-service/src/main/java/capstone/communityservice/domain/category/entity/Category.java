package capstone.communityservice.domain.category.entity;

import capstone.communityservice.domain.server.entity.Server;
import capstone.communityservice.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@SQLDelete(sql = "UPDATE category SET deleted = true WHERE category_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id")
    private Server server;

    @Column(nullable = false)
    private String name;

    private boolean open = Boolean.TRUE;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    //===생성 메서드===//
    public static Category of(Server server, String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        server.addCategory(category);

        return category;
    }

    //===Setter 메소드===//
    public void setServer(Server server) {
        this.server = server;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
