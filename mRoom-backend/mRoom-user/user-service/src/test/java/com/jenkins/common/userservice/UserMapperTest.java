package com.jenkins.common.userservice;

import com.jenkins.common.userinterface.model.User;
import com.jenkins.common.userservice.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public  void testGetSalt(){
        String salt = userMapper.getSalt("614758656@qq.com");
        System.out.println(salt);
    }

    @Test
    public void testInsert()
    {
        User user = new User();
        user.setPassword("123");
        user.setSalt("666");
        user.setEmail("61111@qq.com");
        user.setPhone("123123123");
        userMapper.insertUser(user);
        System.out.println(user.getId());
    }
}
