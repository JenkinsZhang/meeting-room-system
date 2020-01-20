package com.jenkins.common.zuulgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication(scanBasePackages = {"com.jenkins.common.authservice.utils", "com.jenkins.common.zuulgateway"})
@EnableDiscoveryClient
public class MRoomGatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(MRoomGatewayApplication.class,args);
    }
}