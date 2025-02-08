package com.bithealth.controllers;

import com.bithealth.dto.UserRegistrationDTO;
import com.bithealth.entities.User;
import com.bithealth.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    // Upload document

    //
}
