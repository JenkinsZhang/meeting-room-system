package com.jenkins.common.userservice.service;


import com.jenkins.common.userinterface.model.User;
import com.jenkins.common.userinterface.model.UserRole;
import com.jenkins.common.userservice.mapper.UserMapper;
import com.jenkins.common.userservice.mapper.UserRoleMapper;
import com.jenkins.common.userservice.utils.JwtUtil;
import com.jenkins.common.userservice.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jenkinszhang
 * @version 1.0
 *
 * Main service for User action, including various of roles' actions
 * for user,admin,tester,developer
 *
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private JwtUtil jwtUtil;

    public User queryUser(String email, String password) {
        return userMapper.queryUser(email, password);
    }

    public List<User> selectAllUsers() {
        return userMapper.selectAllUsers();
    }

    /**
     * This method does:
     * adds a new user into the database and
     * assign an initial role "user" for him
     *
     * @param user The User object input by the browser {@link User}
     * @return {@code -1}: action failed
     *          else: action succeeded
     */
    public int addUser(User user) {
        User user1 = userMapper.selectUserByEmail(user.getEmail());
        String a = new String();
        if (user1 !=null)
        {
            return -1;
        }
        String salt = BCrypt.gensalt();
        user.setSalt(salt);
        String password = user.getPassword();
        String hashed = BCrypt.hashpw(password, salt);
        user.setPassword(hashed);
        user.setActive(0);
        int insertUser = userMapper.insertUser(user);
        if(insertUser != 1)
        {
            return -1;
        }

        UserRole userRole = new UserRole();
        userRole.setUser_id(user.getId());
        userRole.setRole_id(1);
        int insertUserRole = userRoleMapper.insertUserRole(userRole);
        if( insertUserRole != 1)
        {
            return -2;
        }

//        System.out.println(user);
        return 1;
    }

    public int deleteByEmail(String email) {
        return userMapper.deleteByEmail(email);
    }


    public int updateByEmail(User user) {
        return userMapper.updateSelective(user);
    }

    public int activateUser(String email) {
        User targetUser = new User();
        targetUser.setEmail(email);
        targetUser.setActive(1);
        return userMapper.updateSelective(targetUser);
    }

    public String getSaltByEmail(String email) {
        return userMapper.getSalt(email);
    }

    public int sendVerificationEmail(User user){
        String email = user.getEmail();
        String username = user.getUsername();
        String token = jwtUtil.creatActivationToken(email);
        String url = "http://localhost:8080/activation?token="+token;
        return mailUtil.sendHtmlMail("noreplyz@163.com",email,"Test Mail",username,url);
    }
}
