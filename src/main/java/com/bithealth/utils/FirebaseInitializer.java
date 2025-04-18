package com.bithealth.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Configuration
public class FirebaseInitializer {

    @PostConstruct
    public void initializeFirebase() throws IOException {
        String b64 = System.getenv("FIREBASE_SA_B64");
        InputStream credsStream;
        if (b64 != null && !b64.isEmpty()) {
            byte[] jsonBytes = Base64.getDecoder().decode(b64);
            credsStream = new ByteArrayInputStream(jsonBytes);
        } else {
            credsStream = getClass().getClassLoader()
                    .getResourceAsStream("bithealth.json");
        }
        if (credsStream == null) {
            throw new IllegalStateException("No Firebase creds provided");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(credsStream))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
