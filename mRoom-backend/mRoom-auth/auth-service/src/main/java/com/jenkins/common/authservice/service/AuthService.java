package com.jenkins.common.authservice.service;


import com.jenkins.common.authinterface.entity.AuthUser;
import com.jenkins.common.authinterface.model.UserInfo;
import com.jenkins.common.authservice.client.UserClient;
import com.jenkins.common.authservice.mapper.AuthUserMapper;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.userinterface.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class AuthService {


    private JwtUtil jwtUtil;

    private UserClient userClient;

    private AuthUserMapper authUserMapper;

    @Autowired
    public AuthService(JwtUtil jwtUtil, UserClient userClient, AuthUserMapper authUserMapper) {
        this.jwtUtil = jwtUtil;
        this.userClient = userClient;
        this.authUserMapper = authUserMapper;
    }


    public Map<String, Object> issueToken(String email, String password) {


        Map<String, Object> map = new HashMap<>();
        String salt = authUserMapper.getSaltByEmail(email);
////        try{
////            String passwordStored = BCrypt.hashpw(password, salt);
////        }catch (Exception e)
////        {
////            map.put("status", "fail");
////            map.put("msg","Wrong email or password!");
////            return map;
////        }
        String passwordStored = BCrypt.hashpw(password, salt);
        AuthUser authUser = authUserMapper.queryUser(email, passwordStored);
        System.out.println(authUser);
//        User user = userClient.queryUser(email, password);

        /*
            If there is no user, return an empty map
         */
        if (authUser == null) {
            map.put("status", "fail");
            map.put("msg","Wrong email or password!");
            return map;
        }

        /*
            If the user account is inactive
         */
        if(authUser.getActive() !=1)
        {
            map.put("status","fail");
            map.put("msg","Please active your account via email");
            return map;
        }

//        System.out.println(user);

        /*
            create UserInfo object
         */
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(authUser.getEmail());
        userInfo.setUsername(authUser.getUsername());

        /*
            get user's roles
         */
        List<Integer> userRoleIds = authUserMapper.getUserRoles(authUser.getId());
        Integer userRoleId = userRoleIds.get(0);
        System.out.println(userRoleId);
        String roleName = authUserMapper.getRoleName(userRoleId);

        userInfo.setRoleID(userRoleId);
        userInfo.setRoleName(roleName);

//        System.out.println(userInfo);
        String token = jwtUtil.createToken(userInfo);
        map.put("status","ok");
        map.put("token", token);
        map.put("userInfo", userInfo);
        return map;
    }

    public int activateUser(String email)
    {
        int activate = userClient.activateUser(email);
        return activate;
    }

}
