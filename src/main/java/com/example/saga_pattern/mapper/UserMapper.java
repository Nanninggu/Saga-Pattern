package com.example.saga_pattern.mapper;

import com.example.saga_pattern.dto.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO public.user(username, email, state) VALUES(#{username}, #{email}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Delete("DELETE FROM public.user WHERE id = #{id}")
    void delete(User user);
}