package com.github.backend.repository.mysql;

import com.github.backend.entity.mysql.DormitoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DormitoryRepository extends JpaRepository<DormitoryEntity, String> {
    @Query("SELECT d FROM DormitoryEntity d JOIN d.user u WHERE u.username = :username")
    DormitoryEntity findByUsername(@Param("username") String username);
}
