package com.jenkins.common.userservice;

import com.jenkins.common.userinterface.entity.User;
import com.jenkins.common.userinterface.model.Account;
import com.jenkins.common.userservice.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void getUserByPageTest()
    {
        User user = new User();
        user.setUsername("张");
        user.setPhone("13818397818");
        List<Account> userByPage = userMapper.getUserByPage(0, 10,user);
        System.out.println(userByPage);
    }

    @Test
    public void getTotalTest(){
        User user = new User();
        user.setUsername("张");
        user.setPhone("13818397818");
        int total = userMapper.getTotal(user);
        System.out.println(total);
    }
}
