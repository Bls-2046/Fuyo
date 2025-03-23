package com.github.backend.repository;

import com.github.backend.entity.WeChatUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * WeChatUser 表
 */
@Repository
public interface WeChatUserRepository extends JpaRepository<WeChatUserEntity, String> {
    // 根据 nickname 值进行查找并返回查找到的值
    @Query("SELECT WeChatUser.nickname FROM WeChatUserEntity WeChatUser WHERE WeChatUser.nickname = :nickname")
    String findNicknameByNickname(String nickname);
}
