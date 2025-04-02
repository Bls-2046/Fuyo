package com.github.backend.repository;

import com.github.backend.entity.mysql.YiyanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YiyanRepository extends JpaRepository<YiyanEntity, Long> {
    @Query(value = "SELECT sentence FROM yiyan ORDER BY RAND() LIMIT 1",
            nativeQuery = true)
    Optional<String> findRandomSentence();
}
