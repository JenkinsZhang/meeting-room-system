package com.jenkins.common.authservice.controller;


import com.jenkins.common.authservice.client.UserClient;
import com.jenkins.common.authservice.model.UserInfo;
import com.jenkins.common.authservice.service.AuthService;
import com.jenkins.common.authservice.utils.JwtUtil;
import com.jenkins.common.bookingservice.model.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.datatransfer.StringSelection;
import java.util.List;

@RestController
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authentication")
    public ResultVo authentication(@RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   HttpServletResponse response) {
        String token = authService.issueToken(email, password);
        if (token == null) {
            response.setStatus(401);
            return ResultVo.error(401, "Wrong email or password!");
        }
        response.setHeader("access-token", token);
        return ResultVo.ok("Login success!");
    }

    @GetMapping("/verify")
    public ResultVo verify(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("access-token");
        if (token == null) {
            response.setStatus(401);
            return ResultVo.error(401, "Unauthorized!");
        }
        UserInfo userInfo = jwtUtil.verifyToken(token, response);
        if (userInfo != null) {
            return ResultVo.ok("valid token!", userInfo);
        }
        return ResultVo.error(401, "Unauthorized!");

    }
}
