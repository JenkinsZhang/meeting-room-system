package com.jenkins.common.authservice.client;


import com.jenkins.common.userinterface.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "booking-service")
public interface UserClient extends UserApi {
}
