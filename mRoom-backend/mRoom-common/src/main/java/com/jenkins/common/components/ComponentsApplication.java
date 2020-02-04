package com.jenkins.common.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication(scanBasePackages = "com.jenkins.common.components")
public class ComponentsApplication {


    @Autowired
    private RedisTemplate redisTemplate;


    public static void main(String[] args) {
        SpringApplication.run(ComponentsApplication.class,args);

    }
}
