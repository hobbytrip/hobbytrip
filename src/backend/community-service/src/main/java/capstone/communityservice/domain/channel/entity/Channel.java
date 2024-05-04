package capstone.communityservice.domain.channel.entity;

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
@SQLDelete(sql = "UPDATE channel SET deleted = true WHERE channel_id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id")
    private Server server;

    @Enumerated(value = EnumType.STRING)
    private ChannelType channelType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted = Boolean.FALSE;

    public static Channel of(Server server, Long categoryId, ChannelType channelType, String name) {
        Channel channel = new Channel();
        channel.setCategoryId(categoryId);
        channel.setChannelType(channelType);
        channel.setName(name);
        server.addChannel(channel);

        return channel;
    }

    //===Setter 메서드===//
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void modifyChannel(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
}
