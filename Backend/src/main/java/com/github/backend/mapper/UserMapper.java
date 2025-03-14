package com.github.backend.mapper;

import com.github.backend.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 查
    @Select("select username, password, id, name, department, email, phone, cookie from user where id = #{id}")
    User getUserInfoByUser(String username);

    // 增
    @Insert("insert into user(username, password, id, name, department, email, phone, cookie)" +
    " values(#{username}, #{password}, #{id}, #{name}, #{department}, #{email}, #{phone}, #{cookie})")
    User addUser(User user);
}
