package com.bithealth.utils;

import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Component
public class JwtValidator {

    public FirebaseToken validateToken(String idToken) throws Exception {
        try {
            // Verify the Firebase ID token
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (Exception e) {
            throw new Exception("Invalid or expired token");
        }
    }
}