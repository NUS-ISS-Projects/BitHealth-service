package com.bithealth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithealth.dto.UserRegistrationDTO;
import com.bithealth.entities.User;
import com.bithealth.services.AuthService;
import com.bithealth.utils.FirebaseTokenValidator;
import com.google.firebase.auth.FirebaseAuthException;;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    private FirebaseTokenValidator firebaseTokenValidator;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getProfile(@PathVariable String email) {
        User user = authService.getUserProfile(email);
        return ResponseEntity.ok(user); // 200 OK
    }
    //
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
     
            // Extract the token from the Authorization header
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

           // Extract and validate the user email from the token
            String email = firebaseTokenValidator.validateTokenAndGetEmail(token);
            User user = authService.getUserProfile(email);
            return ResponseEntity.ok(user);
         }
}
