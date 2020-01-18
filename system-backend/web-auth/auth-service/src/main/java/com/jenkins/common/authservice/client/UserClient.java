package com.jenkins.common.authservice.client;


import com.jenkins.common.userinterface.api.UserApi;
import com.jenkins.common.userinterface.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "user-service")
public interface UserClient extends UserApi {

}
