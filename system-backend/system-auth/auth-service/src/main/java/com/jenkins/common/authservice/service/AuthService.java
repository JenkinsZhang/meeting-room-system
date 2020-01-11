package com.jenkins.common.authservice.service;


import com.jenkins.common.authcommon.model.UserInfo;
import com.jenkins.common.authcommon.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;


}
