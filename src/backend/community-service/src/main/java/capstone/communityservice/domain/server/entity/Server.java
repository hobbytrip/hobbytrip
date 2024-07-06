package capstone.communityservice.domain.server.entity;

import capstone.communityservice.domain.category.entity.Category;
import capstone.communityservice.domain.channel.entity.Channel;
import capstone.communityservice.domain.user.entity.User;
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
@SQLDelete(sql = "UPDATE server SET deleted = true WHERE server_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Server extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String profile;

    private String description;

    @Column(nullable = false)
    private Long managerId;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    @Column(nullable = false)
    private boolean open = Boolean.FALSE;

    @OneToMany(mappedBy = "server") // ServerUser가 Server, User 둘다 부모로 갖고있기 때문에 orphanRemoval 사용 고민.
    private List<ServerUser> serverUsers = new ArrayList<>();

    @OneToMany(mappedBy = "server")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "server")
    private List<Channel> channels = new ArrayList<>();

    //===연관 관계 메서드===//
    public void addServerUser(ServerUser serverUser){
        getServerUsers().add(serverUser);
        serverUser.setServer(this);
    }

    public void addCategory(Category category){
        getCategories().add(category);
        category.setServer(this);
    }

    public void addChannel(Channel channel){
        getChannels().add(channel);
        channel.setServer(this);
    }

    //===생성 메서드===//
    public static Server of(String name, String profile, Long managerId){
        Server server = new Server();

        server.setName(name);
        server.setProfile(profile);
        server.setManagerId(managerId);
        return server;
    }

    //===Setter 메서드===//
    public void setServer(String name, String profile, boolean open, String description){
        this.name = name;
        this.profile = profile;
        this.open = open;
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public void setOpen(boolean open) { this.open = open; }

    public void setDescription(String description) {
        this.description = description;
    }
}
