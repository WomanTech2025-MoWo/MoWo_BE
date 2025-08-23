package com.womantech.mowo.domain.member.repository;

import com.womantech.mowo.domain.member.entity.Members;
import com.womantech.mowo.domain.member.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notifications, Long> {
    List<Notifications> findByMembers(Members member);
    
    @Query("SELECT n FROM Notifications n WHERE n.members = :member AND n.createdAt >= :startOfDay AND n.createdAt < :endOfDay")
    List<Notifications> findByMembersAndCreatedAtBetween(@Param("member") Members member, 
                                                        @Param("startOfDay") LocalDateTime startOfDay, 
                                                        @Param("endOfDay") LocalDateTime endOfDay);
}