package com.capstone.notificationservice.domain.server.repository;

import com.capstone.notificationservice.domain.server.entity.ServerNotification;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServerNotificationRepository extends JpaRepository<ServerNotification, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ServerNotification sn WHERE sn.receiver.userId = :userId AND sn.serverId = :serverId")
    void deleteByUserIdAndServerId(@Param("userId") Long userId,@Param("serverId") Long serverId);

    @Query("SELECT DISTINCT n.serverId FROM ServerNotification n WHERE n.receiver.userId = :userId")
    List<Long> findDistinctServerIds(@Param("userId") Long userId);
}
