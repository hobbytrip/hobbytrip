package capstone.communityservice.domain.channel.repository;

import capstone.communityservice.domain.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
