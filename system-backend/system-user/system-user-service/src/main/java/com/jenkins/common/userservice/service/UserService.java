package com.jenkins.common.userservice.service;


import com.jenkins.common.userinterface.model.User;
import com.jenkins.common.userservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        String salt = BCrypt.gensalt();
        user.setSalt(salt);
        String password = user.getPassword();
        String hashpw = BCrypt.hashpw(password, salt);
        user.setPassword(hashpw);
        System.out.println(user);
        return userMapper.insertUser(user);
    }

    public int deleteByEmail(String email) {
        return userMapper.deleteByEmail(email);
    }

    public int updateByEmail(User user) {
        return userMapper.updateByEmail(user);
    }

    public String getSaltByEmail(String email) {
        return userMapper.getSalt(email);
    }
}
