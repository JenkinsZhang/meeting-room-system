package com.jenkins.common.mapper;


import com.jenkins.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component("userMapper")
public interface UserMapper {

    public User selectUserById(@Param("id") int id);
}
