package capstone.communityservice.domain.server.entity;

import capstone.communityservice.domain.user.entity.User;
import capstone.communityservice.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@SQLDelete(sql = "UPDATE server_user SET deleted = true WHERE server_user_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "server_user")
public class ServerUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    //===생성 메서드===//
    public static ServerUser of(Server server, User user) {
        ServerUser serverUser = new ServerUser();

        serverUser.setName(user.getName());

        user.addServerUser(serverUser);
        server.addServerUser(serverUser);

        return serverUser;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setName(String name) {
        this.name = name;
    }
}
