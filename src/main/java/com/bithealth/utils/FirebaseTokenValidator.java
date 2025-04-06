package com.bithealth.utils;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Component
public class FirebaseTokenValidator {

    public Authentication validateToken(String token) {
        try {
            // Verify the Firebase token
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

            // Extract user details from the token
            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();

            // Create an Authentication object
            return new UsernamePasswordAuthenticationToken(
                uid,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}