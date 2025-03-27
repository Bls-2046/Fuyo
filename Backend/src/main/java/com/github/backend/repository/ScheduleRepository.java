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

    ScheduleEntity findByIdAndUserEntityUsername(String id, String userEntity_username);
}
