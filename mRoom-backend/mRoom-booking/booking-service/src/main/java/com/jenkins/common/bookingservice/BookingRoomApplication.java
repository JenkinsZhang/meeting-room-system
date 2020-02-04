package com.jenkins.common.bookingservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.jenkins.common.bookingservice.mapper")

public class BookingRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingRoomApplication.class, args);
    }
}
