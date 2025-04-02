package com.github.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface YiyanMapper {
    @Select("""
        SELECT sentence FROM yiyan
        WHERE id >= (FLOOR(RAND() * (SELECT MAX(id) FROM yiyan)))
        LIMIT 1
        """)
    Optional<String> findRandomSentence();
}
