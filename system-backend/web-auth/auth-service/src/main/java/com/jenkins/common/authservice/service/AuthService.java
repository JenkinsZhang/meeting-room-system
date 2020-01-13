package com.jenkins.common.authservice.service;


import com.jenkins.common.authservice.client.UserClient;
import com.jenkins.common.authservice.model.UserInfo;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.userinterface.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserClient userClient;

    public String issueToken(String email, String password) {
        User user = userClient.queryUser(email, password);
        if (user == null) {
            return null;
        }
        System.out.println(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setUsername(user.getPassword());
        System.out.println(userInfo);
        String token = jwtUtil.createToken(userInfo);
        return token;
    }

}
