package com.jenkins.common.authservice;

import com.jenkins.common.authcommon.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testConfig() {
        System.out.println(jwtUtil.getSECRET_KEY());
    }
}
