package com.jenkins.common.authservice.controller;


import com.auth0.jwt.JWT;
import com.jenkins.common.authinterface.model.UserInfo;
import com.jenkins.common.authservice.service.AuthService;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.components.model.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@Api(tags = "token认证接口")
public class AuthController {



    private AuthService authService;

    private JwtUtil jwtUtil;

    private HttpServletRequest request;

    @Autowired
    public AuthController(AuthService authService, JwtUtil jwtUtil, HttpServletRequest request) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.request = request;
    }

    @ApiOperation(value = "用户认证", notes = "根据邮箱和密码对用户进行认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "email", value = "用户邮箱", required = true),
            @ApiImplicitParam(paramType = "query", name = "password", value = "登陆密码", required = true)
    })
    @PostMapping("/login")
    public ResultVo authentication(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   HttpServletResponse response) {
        Map<String, Object> resultMap = authService.issueToken(email, password);
        String status = (String) resultMap.get("status");
        if(!status.equals("ok")){
            String msg = (String) resultMap.get("msg");
            return ResultVo.error(401,msg);
        }
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        System.out.println("这是测试哦哦哦"+request.getHeader("access-token"));
        String token = (String) resultMap.get("token");
        UserInfo userInfo = (UserInfo) resultMap.get("userInfo");
        response.setHeader("access-token",token);
        return ResultVo.ok("login success",userInfo);
    }

    @GetMapping("/verify")
    public ResultVo verifyToken(@RequestHeader("access-token") String token) {
        if (token == null) {
            return ResultVo.error(401, "Unauthorized!");
        }
        UserInfo userInfo = jwtUtil.verifyToken(token);
        if (userInfo != null) {
            return ResultVo.ok("valid token!", userInfo);
        }
        return ResultVo.error(401, "Unauthorized!");

    }
    @GetMapping("/refresh")
    public ResultVo refreshToken(@RequestHeader("access-token") String token){
        String refreshToken = jwtUtil.refreshToken(token);
        if(refreshToken == null){
            return ResultVo.error(401,"Invalid Token!");
        }
        return ResultVo.ok("Token Refreshed!",refreshToken);
    }


    @PostMapping("/activate")
    public ResultVo verifyActivation(@RequestParam("email") String email,
                           @RequestParam("activateToken")String activateToken)
    {
        Date now = DateTime.now().toDate();
        Date expiresAt = JWT.decode(activateToken).getExpiresAt();
        if(now.after(expiresAt)){
            return ResultVo.error(16001,"The activation time has expired, please login again to get the activation link!");
        }
        int activate = authService.activateUser(email);
        if(activate != 1)
        {
            return ResultVo.error(16002,"Activation failed! Please contact administrator!");
        }
        return ResultVo.ok("activation success!");

    }
}
