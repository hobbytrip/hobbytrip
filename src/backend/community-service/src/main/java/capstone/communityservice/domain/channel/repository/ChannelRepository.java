package capstone.communityservice.domain.channel.repository;

import capstone.communityservice.domain.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query("select c from Channel c where c.server.id = :serverId")
    List<Channel> findByServerId(Long serverId);
}
