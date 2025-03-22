package com.github.backend.repository;

import com.github.backend.entity.TabletimeEntity;
import com.github.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabletimeRepository extends JpaRepository<TabletimeEntity, String> {
    List<TabletimeEntity> findByUserEntityIdAndX(String userEntity_id, int x);
}
