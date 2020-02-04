package com.jenkins.common.authservice.service;


import com.jenkins.common.authservice.client.UserClient;
import com.jenkins.common.authservice.model.UserInfo;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.userinterface.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserClient userClient;

    public Map<String, Object> issueToken(String email, String password) {


        Map<String, Object> map = new HashMap<>();
        User user = userClient.queryUser(email, password);

        /*
            If there is no user, return an empty map
         */
        if (user == null) {
            map.put("status", "fail");
            map.put("msg","Wrong email or password!");
            return map;
        }

        /*
            If the user account is inactive
         */
        if(user.getActive() !=1)
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
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());

        /*
            get user's roles
         */
        List<Integer> userRoleId = userClient.getUserRoleId(user.getId());
        System.out.println(userRoleId);
        List<String> roles = new LinkedList<>();
        for (Integer role_id : userRoleId) {
            roles.add(userClient.getRoleName(role_id));
        }
        userInfo.setRole(roles);

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
