package com.jenkins.common.authservice;

import com.jenkins.common.authservice.client.UserClient;
import com.jenkins.common.authservice.controller.AuthController;
import com.jenkins.common.authservice.service.AuthService;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.userinterface.model.User;
import com.netflix.discovery.converters.Auto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    UserClient userClient;

    @Autowired
    AuthService authService;

    @Autowired
    JwtUtil jwtUtil;

    @Test
    public void testUserApi()
    {
        List<User> users = userClient.selectAllUsers();
        System.out.println(users);
    }

    @Test
    public void testRoleApi()
    {
        String roleName = userClient.getRoleName(1);
        System.out.println(roleName);
    }

    @Test
    public void testAuthService()
    {
        Map<String, Object> stringObjectMap = authService.issueToken("614758656@qq.com", "123");
        System.out.println(stringObjectMap);
    }




}
