package com.example.jwtserver.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jwtserver.config.JwtConfiguration;
import com.example.jwtserver.exception.InvalidJWTTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    JwtConfiguration jwtConfiguration;

    public String getToken() {
        Algorithm algorithm = Algorithm.HMAC512(jwtConfiguration.getSecret());

        try {
            return JWT.create()
                    .withIssuer("auth0")
                    .withClaim("iv-user", "sburz")
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            System.out.println("could not create token: " + ex.getMessage());
            return null;
        }
    }

    public String getProtectResource(String token) {
        if (verifyToken(token) != null && verifyToken(token).getToken() != null) {
            return "Protected resource";
        } else {
            throw new InvalidJWTTokenException("Token not valid");
        }
    }

    private DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(jwtConfiguration.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            throw new InvalidJWTTokenException(exception.getMessage());
        }
    }

}
