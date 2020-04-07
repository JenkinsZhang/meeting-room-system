package com.jenkins.common.userservice.service;


import com.jenkins.common.components.model.AccountVo;
import com.jenkins.common.components.model.ResultVo;
import com.jenkins.common.userinterface.entity.Role;
import com.jenkins.common.userinterface.entity.User;
import com.jenkins.common.userinterface.entity.UserRole;
import com.jenkins.common.userinterface.model.Account;
import com.jenkins.common.userservice.client.AuthClient;
import com.jenkins.common.userservice.mapper.RoleMapper;
import com.jenkins.common.userservice.mapper.UserMapper;
import com.jenkins.common.userservice.mapper.UserRoleMapper;
import com.jenkins.common.userservice.utils.JwtUtil;
import com.jenkins.common.userservice.utils.MailUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private UserMapper userMapper;

    private UserRoleMapper userRoleMapper;

    private RoleMapper roleMapper;

    private MailUtil mailUtil;

    private JwtUtil jwtUtil;

    private AuthClient authClient;

    @Autowired
    public UserService(UserMapper userMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper, MailUtil mailUtil, JwtUtil jwtUtil, AuthClient authClient) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.mailUtil = mailUtil;
        this.jwtUtil = jwtUtil;
        this.authClient = authClient;
    }

    public User queryUser(String email, String password) {
        return userMapper.queryUser(email, password);
    }

    public AccountVo getAccounts(Integer page,Integer size,User user){
        List<Account> accounts = userMapper.getUserByPage(page-1, size, user);
        for (Account account : accounts) {
            long id = account.getId();
            account.setViewId(String.format("%07d",id));
            int active = account.getActive();
            account.setStatus(active == 1 ? "Activated" :"Unactivated");
        }

        int total = userMapper.getTotal(user);
        AccountVo accountVo = new AccountVo();
        accountVo.setAccounts(accounts);
        accountVo.setTotal((long) total);
        return accountVo;
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

    public int deleteUser(String email)
    {
        User user = new User();
        user.setEmail(email);
        user.setActive(0);

        return userMapper.updateSelective(user);
    }

    public int updateUser(User user) {
        int userId = user.getId();
        String userEmail = user.getEmail();
        User existingUser = userMapper.selectUserByEmail(userEmail);
        if(existingUser == null)
        {
            return userMapper.updateSelective(user);
        }else {
            if(existingUser.getId() == userId)
            {
                return userMapper.updateSelective(user);
            }
            else
            {
                return -1;
            }
        }


    }

    public int activateByEmail(String email)
    {
        User user = new User();
        user.setActive(1);
        user.setEmail(email);
        return userMapper.updateSelective(user);
    }
    public int activateUser(String token) {

        if(jwtUtil.verifyActivationToken(token) == null)
        {
            return -1;
        }
        User targetUser = new User();
        String email = jwtUtil.verifyActivationToken(token);
        targetUser.setEmail(email);
        targetUser.setActive(1);
        return userMapper.updateSelective(targetUser);
    }

    public String getSaltByEmail(String email) {
        return userMapper.getSalt(email);
    }

    public String getEmailById(int id){
        String email = userMapper.getEmail(id);
        return email;
    }

    public List<Account> getUserByPage(Integer page,Integer size,User user)
    {
        page = page-1;
        return userMapper.getUserByPage(page,size,user);
    }
    public int sendVerificationEmail(User user){
        String email = user.getEmail();
        String username = user.getUsername();
        String token = jwtUtil.creatActivationToken(email);
        String url = "http://localhost:8080/activation?token="+token;
        return mailUtil.sendHtmlMail("noreplyz@163.com",email,"Test Mail",username,url);
    }


    public boolean checkEmail(String email){
        User user = userMapper.selectUserByEmail(email);
        return user == null;
    }

    public String getPhone(String email) {
        User user = userMapper.selectUserByEmail(email);
        return user.getPhone();
    }

    public int changePhone(String email,String phone) {
        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setActive(1);
        return userMapper.updateSelective(user);

    }

    public int changeUsername(String email,String username){
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setActive(1);
        return userMapper.updateSelective(user);
    }

    public int changePassword(String email,String oldPassword,String newPassword)
    {
        String oldSalt = getSaltByEmail(email);
        try {
            oldPassword = BCrypt.hashpw(oldPassword, oldSalt);
            User user = queryUser(email, oldPassword);
            if(user != null)
            {
                User updateUser = new User();
                String newSalt = BCrypt.gensalt();
                String hashedNewPassword =  BCrypt.hashpw(newPassword, newSalt);
                updateUser.setEmail(email);
                updateUser.setSalt(newSalt);
                updateUser.setPassword(hashedNewPassword);
                updateUser.setActive(1);
                return userMapper.updateSelective(updateUser);
            }
            else
            {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
