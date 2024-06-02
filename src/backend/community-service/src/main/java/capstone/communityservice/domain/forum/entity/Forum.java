package capstone.communityservice.domain.forum.entity;

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
@SQLDelete(sql = "UPDATE forum SET deleted = true WHERE forum_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Forum extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forum_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long channelId;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    private ForumCategory category;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

//    @OneToMany(mappedBy = "forum", orphanRemoval = true)
    @OneToMany(mappedBy = "forum")
    private List<File> files = new ArrayList<>();

    //===연관 관계 메서드===//

    public void addFile(File file){
        getFiles().add(file);
        file.setForum(this);
    }

    //===생성 메서드===//

    public static Forum of(Long channelId, String title, User user, String content, ForumCategory category){
        Forum forum = new Forum();

        forum.setChannelId(channelId);
        forum.setTitle(title);
        forum.setUser(user);
        forum.setContent(content);
        forum.setCategory(category);

        return forum;
    }

    //===Setter 메서드===//
    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(ForumCategory category){
        this.category = category;
    }

    public void setForum(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
