package com.jenkins.common.userservice.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public String verifyActivationToken(String token)
    {

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            String email = verify.getClaim("email").asString();
            return email;
        }catch (Exception e)
        {
            return null;
        }
    }
}
