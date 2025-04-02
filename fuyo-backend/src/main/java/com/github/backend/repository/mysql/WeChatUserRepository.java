package com.github.backend.repository.mysql;

import com.github.backend.entity.mysql.WeChatUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * WeChatUser 表
 */
@Repository
public interface WeChatUserRepository extends JpaRepository<WeChatUserEntity, String> {
    // 根据 nickname 值进行查找并返回查找到的值
    WeChatUserEntity findNicknameByNickname(String nickname);
}
