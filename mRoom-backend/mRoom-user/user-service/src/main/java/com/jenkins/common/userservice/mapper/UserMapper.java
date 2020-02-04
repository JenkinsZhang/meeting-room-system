package com.jenkins.common.userservice.mapper;

import com.jenkins.common.userinterface.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    User queryUser(@Param("email") String email, @Param("password") String password);

    User selectUserByEmail(@Param("email") String email);

    String getSalt(@Param("email") String email);

    int insertUser(@Param("user") User user);

    int updateSelective(@Param("user") User user);

    int deleteByEmail(String email);

    List<User> selectAllUsers();


}
