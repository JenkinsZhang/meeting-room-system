package com.jenkins.common.roomService;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.annotation.PostConstruct;


/**
 * @author jenkinszhang
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "com.jenkins.common.roomService.mapper")
public class RoomServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomServiceApplication.class,args);
    }

}
