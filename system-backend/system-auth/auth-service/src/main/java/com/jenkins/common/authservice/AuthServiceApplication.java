package com.jenkins.common.authservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.jenkins.common")
@EnableDiscoveryClient
public class AuthServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}
