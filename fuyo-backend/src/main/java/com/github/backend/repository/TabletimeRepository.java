package com.github.backend.repository;

import com.github.backend.entity.mysql.TabletimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabletimeRepository extends JpaRepository<TabletimeEntity, String> {
    List<TabletimeEntity> findByUserEntityUsernameAndX(String userEntity_username, int x);
}
