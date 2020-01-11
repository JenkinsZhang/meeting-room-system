package com.jenkins.common.authcommon;


import com.jenkins.common.authcommon.utils.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class AuthCommonTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void test1() {
        System.out.println(jwtUtil.getSECRET_KEY());
    }
}
