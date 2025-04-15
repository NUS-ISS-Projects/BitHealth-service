package com.bithealth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;

public class JwtUtil {

    public static String getEmailFromToken(String token) {
        try {
            // Decode the token
            return JWT.decode(token).getClaim("email").asString();
        } catch (JWTDecodeException e) {
            throw new RuntimeException("Invalid token");
        }
    }
}