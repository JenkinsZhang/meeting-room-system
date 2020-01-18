package com.jenkins.common.authservice.service;


import com.jenkins.common.authservice.client.UserClient;
import com.jenkins.common.authservice.model.UserInfo;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.userinterface.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        if (user == null) {
            map.put("token", null);
            return map;
        }
        System.out.println(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
//        System.out.println(userInfo);
        String token = jwtUtil.createToken(userInfo);
        map.put("token", token);
        map.put("userInfo", userInfo);
        return map;
    }

}
