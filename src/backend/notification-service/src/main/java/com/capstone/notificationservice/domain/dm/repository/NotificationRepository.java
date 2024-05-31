package com.capstone.notificationservice.domain.dm.repository;

import com.capstone.notificationservice.domain.dm.entity.DmNotification;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<DmNotification, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM DmNotification sn WHERE sn.receiver.userId = :userId AND sn.dmRoomId = :dmRoomId")
    void deleteByUserIdAndDmRoomId(@Param("userId") Long userId, @Param("dmRoomId") Long dmRoomId);
    @Query("SELECT DISTINCT n.dmRoomId FROM DmNotification n WHERE n.receiver.userId = :userId")
    List<Long> findDistinctDmRoomIds(@Param("userId") Long userId);

}
