package com.jenkins.common.authservice.client;


import com.jenkins.common.userinterface.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "user-service")
public interface UserClient extends UserApi {

}
