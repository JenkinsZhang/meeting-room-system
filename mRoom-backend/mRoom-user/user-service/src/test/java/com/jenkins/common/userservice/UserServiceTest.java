package com.jenkins.common.userservice;

import com.jenkins.common.userinterface.model.User;
import com.jenkins.common.userservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;



    @Test
    public void testRegistry(){
        User user = new User();
        user.setEmail("614758656@qq.com");
        user.setPassword("123456");
        int i = userService.addUser(user);
        System.out.println(i);
    }

    @Test
    public void testAll(){
        List<User> users = userService.selectAllUsers();
        System.out.println(users);
    }

    @Test
    public void testMail(){
        User user = new User();
        user.setEmail("614758656@qq.com");
        user.setUsername("zjp牛逼");
        int i = userService.sendVerificationEmail(user);

    }
}
