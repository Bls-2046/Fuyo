package com.github.backend.repository.mysql;

import com.github.backend.entity.mysql.DormitoryEntity;

import com.github.dto.dormitory.FetchDormitoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DormitoryRepository extends JpaRepository<DormitoryEntity, String> {
    @Query("SELECT d.dormitoryId, d.dormNo, d.waterFee, d.electricityFee " +
            "FROM DormitoryEntity d JOIN d.user u WHERE u.username = :username")
    FetchDormitoryResponse.Dormitory findByUsername(@Param("username") String username);
}
