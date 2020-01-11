package com.jenkins.common.userservice.mapper;

import com.jenkins.common.userservice.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    User queryUser(@Param("email") String email, @Param("password") String password);

    int insert(User user);

    int insertSelective(User user);

    int updateByEmail(User user);

    int deleteByEmail(String email);

    List<User> selectAllUsers();

}
