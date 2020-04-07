package com.jenkins.common.userservice.mapper;

import com.jenkins.common.userinterface.entity.User;
import com.jenkins.common.userinterface.model.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    User queryUser(@Param("email") String email, @Param("password") String password);

    User selectUserByEmail(@Param("email") String email);

    String getEmail(@Param("id") int id);

    String getSalt(@Param("email") String email);

    int insertUser(@Param("user") User user);

    int updateSelective(@Param("user") User user);

    int deleteByEmail(String email);

    List<Account> getUserByPage(@Param("page") Integer page, @Param("size") Integer size, @Param("user") User user);

    int getTotal(@Param("user") User user);

}
