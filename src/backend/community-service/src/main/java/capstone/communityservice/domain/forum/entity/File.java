package capstone.communityservice.domain.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id", nullable = false)
    private Forum forum;

    @Column(nullable = false)
    private String fileUrl;

    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

    //===생성 메서드===//
    public static File of(Forum forum, String fileUrl){
        File file = new File();

        forum.addFile(file);
        file.setFileUrl(fileUrl);

        return file;
    }

    //===Setter 메서드===//

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
