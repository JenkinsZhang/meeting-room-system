package com.jenkins.common.userservice.client;

import com.jenkins.common.authinterface.api.AuthApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author JenkinsZhang
 * @date 2020/2/26
 * @description
 */

@Component
@FeignClient(name = "auth-service")
public interface AuthClient extends AuthApi {

}
