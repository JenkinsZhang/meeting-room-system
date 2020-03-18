package com.jenkins.common.bookingservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "com.jenkins.common.bookingservice.mapper")
@EnableFeignClients(basePackages = "com.jenkins.common")
@EnableTransactionManagement
public class BookingRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingRoomApplication.class, args);
    }
}
