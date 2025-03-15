package com.github.backend.repository;

import com.github.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabletimeRepository extends JpaRepository<User.Tabletime, Long> {
}
