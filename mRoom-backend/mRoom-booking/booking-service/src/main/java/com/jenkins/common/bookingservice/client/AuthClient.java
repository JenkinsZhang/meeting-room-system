package com.jenkins.common.bookingservice.client;

import com.jenkins.common.authinterface.api.AuthApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 2020/4/19
 */
@Component
@FeignClient(name = "auth-service")
public interface AuthClient extends AuthApi {
}
