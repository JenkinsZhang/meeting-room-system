package com.jenkins.common.authservice.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jenkins.common.authinterface.model.UserInfo;
import com.jenkins.common.components.model.ResultVo;
import org.joda.time.DateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "auth-service")
public class JwtUtil {

    private String SECRET_KEY;

    private int EXPIRE_TIME;

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public int getEXPIRE_TIME() {
        return EXPIRE_TIME;
    }

    public void setEXPIRE_TIME(int EXPIRE_TIME) {
        this.EXPIRE_TIME = EXPIRE_TIME;
    }

    public String createToken(UserInfo userInfo) {
        List<String> roles = userInfo.getRoles();
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = null;
        Date dateTime = new DateTime().plusMinutes(EXPIRE_TIME).toDate();
        token = JWT.create().withClaim("email", userInfo.getEmail())
                .withClaim("username", userInfo.getUsername())
                .withArrayClaim("roles",roles.toArray(new String[roles.size()]))
                .withExpiresAt(dateTime)
                .sign(algorithm);

        return token;
    }

    public UserInfo verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(verify.getClaim("email").asString());
            userInfo.setUsername(verify.getClaim("username").asString());
            userInfo.setRoles(verify.getClaim("roles").asList(String.class));
            System.out.println(userInfo);
            return userInfo;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }


    public String refreshToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        UserInfo userInfo = verifyToken(token);
        if(userInfo != null)
        {
            String newToken = createToken(userInfo);
            return newToken;
        }
        return null;

    }
}
