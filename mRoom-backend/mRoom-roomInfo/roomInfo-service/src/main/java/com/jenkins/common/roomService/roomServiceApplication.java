package com.jenkins.common.roomService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class roomServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(roomServiceApplication.class,args);
    }
}
