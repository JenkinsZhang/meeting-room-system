package com.jenkins.common.userservice.service;


import com.jenkins.common.userservice.mapper.UserMapper;
import com.jenkins.common.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUser(String email, String password) {
        return userMapper.queryUser(email, password);
    }

    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    public int deleteByEmail(String email) {
        return userMapper.deleteByEmail(email);
    }

    public int updateByEmail(User user) {
        return userMapper.updateByEmail(user);
    }
}
