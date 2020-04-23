package com.jenkins.common.component;


import com.jenkins.common.components.ComponentsApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {ComponentsApplication.class})
@RunWith(SpringRunner.class)
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;


    @Test
    public void test(){
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("111",111);
//        Integer o = (Integer)valueOperations.get("111");
//        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps("111");
//        System.out.println(o);
    }



}
