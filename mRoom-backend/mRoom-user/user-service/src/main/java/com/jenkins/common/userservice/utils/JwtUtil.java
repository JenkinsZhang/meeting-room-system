package com.jenkins.common.userservice.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final static String SECRET = "activationToken";


    public String creatActivationToken(String email)
    {
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        //Expire time is 2h
        Date expire = DateTime.now().plusHours(2).toDate();
        String activationToken = JWT.create().withClaim("email", email)
                .withExpiresAt(expire)
                .sign(algorithm);
        return activationToken;
    }
}
