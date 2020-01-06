package com.jenkins.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookingRoomSeviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingRoomSeviceApplication.class,args);
    }
}
