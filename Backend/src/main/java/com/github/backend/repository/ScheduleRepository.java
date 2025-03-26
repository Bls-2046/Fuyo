package com.github.backend.repository;

import com.github.backend.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {
    List<ScheduleEntity> findByUserEntityUsername(String userEntity_username);

    // Repository 接口
    @Query("SELECT s FROM ScheduleEntity s WHERE " +
            "s.title = :title AND " +
            "s.dateTime BETWEEN :startTime AND :endTime AND " +  // 时间范围
            "s.reminderDateTime BETWEEN :reminderStart AND :reminderEnd AND " +
            "s.description = :description AND " +
            "s.openid = :openid AND " +
            "s.userEntity.username = :username")
    ScheduleEntity findByTitleAndDateTimeAndReminderDateTimeAndDescriptionAndOpenidAndUserEntityUsername(
            @Param("title") String title,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("reminderStart") LocalDateTime reminderStart,
            @Param("reminderEnd") LocalDateTime reminderEnd,
            @Param("description") String description,
            @Param("openid") String openid,
            @Param("username") String username
    );
}
