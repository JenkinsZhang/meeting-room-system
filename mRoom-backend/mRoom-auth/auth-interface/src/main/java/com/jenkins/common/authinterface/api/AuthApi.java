package com.jenkins.common.authinterface.api;

import com.jenkins.common.components.model.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author JenkinsZhang
 * @date 2020/2/26
 * @description
 */
public interface AuthApi {

    @GetMapping("/verify")
    public ResultVo verifyToken(@RequestHeader("access-token") String token) ;

    @GetMapping("/refresh")
    public ResultVo refreshToken(@RequestHeader("access-token") String token);
}
