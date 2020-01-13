package com.jenkins.common.authservice.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jenkins.common.authservice.model.UserInfo;
import com.jenkins.common.bookingservice.model.ResultVo;
import org.joda.time.DateTime;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

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
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String token = null;
        Date dateTime = new DateTime().plusMinutes(EXPIRE_TIME).toDate();
        token = JWT.create().withClaim("email", userInfo.getEmail())
                .withClaim("username", userInfo.getUsername())
                .withExpiresAt(dateTime)
                .sign(algorithm);

        return token;
    }

    public UserInfo verifyToken(String token, HttpServletResponse response) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(verify.getClaim("email").toString());
            userInfo.setUsername(verify.getClaim("username").toString());
            String newToken = createToken(userInfo);
            response.setHeader("access-token", newToken);
            return userInfo;
        } catch (Exception e) {
            try {
                PrintWriter writer = response.getWriter();
                String msg = e.getLocalizedMessage();
                if (e.getClass().isInstance(TokenExpiredException.class)) {
                    response.setStatus(401);
                    ResultVo resultVo = ResultVo.error(500, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else if (e.getClass().isInstance(InvalidClaimException.class)) {
                    response.setStatus(401);
                    ResultVo resultVo = ResultVo.error(501, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else if (e.getClass().isInstance(SignatureVerificationException.class)) {
                    response.setStatus(401);
                    ResultVo resultVo = ResultVo.error(502, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else if (e.getClass().isInstance(AlgorithmMismatchException.class)) {
                    response.setStatus(401);
                    ResultVo resultVo = ResultVo.error(503, msg);
                    writer.write(JSON.toJSONString(resultVo));
                } else {
                    response.setStatus(401);
                    ResultVo resultVo = ResultVo.error(404, msg);
                    writer.write(JSON.toJSONString(resultVo));
                }
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
