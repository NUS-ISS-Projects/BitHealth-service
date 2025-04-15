package com.bithealth.utils;

import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Component
public class FirebaseTokenValidator {

    public String validateTokenAndGetEmail(String token) {
        try {
            // Verify the Firebase token
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);

            // Extract user details from the token
            String email = decodedToken.getEmail();

            // Create an Authentication object
            return email;
        } catch (Exception e) {
            throw new RuntimeException("Invalid token", e);
        }
    }
}