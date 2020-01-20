package com.jenkins.common.registryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MRoomRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MRoomRegistryApplication.class,args);
    }
}
