package com.jenkins.common.userservice;

import com.jenkins.common.userinterface.entity.User;
import com.jenkins.common.userservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testRegistry(){
        User user = new User();
        user.setEmail("614758656@qq.com");
        user.setPassword("123456");
        int i = userService.addUser(user);
        System.out.println(i);
    }



    @Test
    public void testMail(){
        User user = new User();
        user.setEmail("614758656@qq.com");
        user.setUsername("zjp牛逼");
        int i = userService.sendVerificationEmail(user);
        System.out.println(i);
    }

    @Test
    public void sendVerification(){
        userService.sendVerificationCode("614758656@qq.com");
    }

    @Test
    public void testVerifyCode(){
        boolean b = userService.verifyCode("614758656@qq.com","063920");
        System.out.println(b);
    }

    @Test
    public void testGeneral()
    {
        System.out.println(userService.getGeneralUsers());
    }
}
