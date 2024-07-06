package capstone.communityservice.domain.channel.repository;

import capstone.communityservice.domain.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query("select c from Channel c where c.server.id = :serverId")
    List<Channel> findByServerId(Long serverId);

    @Query("select c from Channel c join fetch c.server where c.id = :channelId")
    Optional<Channel> findByIdWithServer(Long channelId);

    @Query(value = "select s.manager_id " +
            "from channel c inner join server s on c.server_id = s.server_id " +
            "where c.channel_id = :channelId",
            nativeQuery = true)
    Long validateChannelManager(Long channelId);

    @Modifying
    @Query("update Channel c set c.deleted = true where c.server.id = :serverId")
    void deleteAllByServerId(Long serverId);
}
