package com.jenkins.common.userservice.mapper;

import com.jenkins.common.userinterface.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Component
public interface UserMapper {

    User queryUser(@Param("email") String email, @Param("password") String password);

    String getSalt(@Param("email") String email);

    int insertUser(@Param("user") User user);

    int updateByEmail(User user);

    int updateSelective(User user);

    int deleteByEmail(String email);

    List<User> selectAllUsers();


}
