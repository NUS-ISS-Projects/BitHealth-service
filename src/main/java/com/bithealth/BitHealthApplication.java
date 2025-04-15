package com.bithealth;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@EnableJpaAuditing
public class BitHealthApplication {

	public static void main(String[] args) {
		try {
            initializeFirebase();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Firebase Admin SDK", e);
        }
		SpringApplication.run(BitHealthApplication.class, args);
	}
	private static void initializeFirebase() throws IOException {
        // Path to the service account key file
        String serviceAccountPath = "src/main/resources/bithealth.json";

        // Load the service account key
        FileInputStream serviceAccount = new FileInputStream(serviceAccountPath);

        // Configure Firebase options
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // Initialize Firebase app
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

}
