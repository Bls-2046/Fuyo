package com.github.backend.repository;

import com.github.backend.entity.mysql.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String> {
    List<ScheduleEntity> findByUserEntityUsername(String userEntity_username);

    ScheduleEntity findByIdAndUserEntityUsername(String id, String userEntity_username);
}
