package com.github.backend.repository;

import com.github.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TabletimeRepository extends JpaRepository<UserEntity.Tabletime, String> {
    List<UserEntity.Tabletime> findByUserEntityId(String userId);
}
