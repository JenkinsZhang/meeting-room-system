package com.jenkins.common.userservice;

import com.jenkins.common.userinterface.model.User;
import com.jenkins.common.userservice.mapper.RoleMapper;
import com.jenkins.common.userservice.mapper.UserMapper;
import com.jenkins.common.userservice.service.UserService;
import com.netflix.discovery.converters.Auto;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @org.junit.Test
    public  void testGetSalt(){
        String salt = userMapper.getSalt("614758656@qq.com");
        System.out.println(salt);
    }

    @org.junit.Test
    public void testRegistry(){
        User user = new User();
        user.setEmail("614758656@qq.com");
        user.setPassword("123456");
        int i = userService.insert(user);
        System.out.println(i);
    }

}
