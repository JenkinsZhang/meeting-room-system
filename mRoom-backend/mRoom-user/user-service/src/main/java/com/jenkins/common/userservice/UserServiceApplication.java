package com.jenkins.common.userservice;


import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.jenkins.common"})
@MapperScan(basePackages = "com.jenkins.common.userservice.mapper")
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = "com.jenkins.common")
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
