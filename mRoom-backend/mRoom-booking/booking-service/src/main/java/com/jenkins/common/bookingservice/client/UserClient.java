package com.jenkins.common.bookingservice.client;

import com.jenkins.common.userinterface.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 29/04/2020
 */

@Component
@FeignClient(name = "user-service")
public interface UserClient extends UserApi {
}
