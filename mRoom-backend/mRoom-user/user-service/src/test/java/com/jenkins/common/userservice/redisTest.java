package com.jenkins.common.userservice;


import com.google.inject.internal.cglib.core.$LocalVariablesSorter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class redisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test1(){
        String code = (String)redisTemplate.opsForHash().get("614758656@qq.com" + "_forgetPassword", "code");
        System.out.println(code);
    }

}
