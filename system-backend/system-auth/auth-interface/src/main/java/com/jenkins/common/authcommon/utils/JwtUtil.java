package com.jenkins.common.authcommon.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jenkins.common.bookingservice.model.ResultVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Component
@PropertySource("classpath:application-auth-common.yml")
@ConfigurationProperties(prefix = "auth-common")
public class JwtUtil {

    private String SECRET_KEY;

    private String EXPIRE_TIME;

    public String getSECRET_KEY() {
        return SECRET_KEY;
    }

    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public String getEXPIRE_TIME() {
        return EXPIRE_TIME;
    }

    public void setEXPIRE_TIME(String EXPIRE_TIME) {
        this.EXPIRE_TIME = EXPIRE_TIME;
    }


    public String createToken(String userName, String phoneNumber) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = null;
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        token = JWT.create().withClaim("userName", userName)
                .withClaim("phoneNumber", phoneNumber)
                .withExpiresAt(date)
                .sign(algorithm);

        return token;
    }

    public boolean verifyToken(String token, HttpServletResponse response) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            return true;
        } catch (Exception e) {
            try {
                PrintWriter writer = response.getWriter();
                String msg = e.getLocalizedMessage();
                System.out.println(e.getClass());
                if (e.getClass().isInstance(TokenExpiredException.class)) {
                    response.setStatus(500);
                    ResultVo resultVo = ResultVo.error(500, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else if (e.getClass().isInstance(InvalidClaimException.class)) {
                    response.setStatus(501);
                    ResultVo resultVo = ResultVo.error(501, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else if (e.getClass().isInstance(SignatureVerificationException.class)) {
                    response.setStatus(502);
                    ResultVo resultVo = ResultVo.error(502, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else if (e.getClass().isInstance(AlgorithmMismatchException.class)) {
                    response.setStatus(503);
                    ResultVo resultVo = ResultVo.error(503, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else {
                    response.setStatus(404);
                    ResultVo resultVo = ResultVo.error(404, msg);
                    writer.write(JSON.toJSONString(resultVo));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
