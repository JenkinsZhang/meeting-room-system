package com.jenkins.common.authservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.jenkins.common.authservice","com.jenkins.common.components"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.jenkins.common")
@MapperScan(basePackages = "com.jenkins.common.authservice.mapper")
public class AuthServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
