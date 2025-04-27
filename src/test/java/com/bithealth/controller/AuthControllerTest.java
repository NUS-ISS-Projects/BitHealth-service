package com.bithealth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bithealth.controllers.AuthController;
import com.bithealth.dto.UserRegistrationDTO;
import com.bithealth.entities.User;
import com.bithealth.services.AuthService;
import com.bithealth.utils.FirebaseTokenValidator;
import com.google.firebase.auth.FirebaseAuthException;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private FirebaseTokenValidator firebaseTokenValidator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Setup security context for tests that use @PreAuthorize
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("Test User");
        registrationDTO.setEmail("test@example.com");
        registrationDTO.setRole("PATIENT");
        registrationDTO.setFirebaseUid("dummy_uid");

        User registeredUser = new User();
        registeredUser.setUserId(1L);
        registeredUser.setName("Test User");
        registeredUser.setEmail("test@example.com");
        registeredUser.setRole(User.Role.PATIENT);
        registeredUser.setFirebaseUid("dummy_uid");

        when(authService.registerUser(registrationDTO)).thenReturn(registeredUser);

        // Act
        ResponseEntity<User> responseEntity = authController.register(registrationDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(registeredUser, responseEntity.getBody());
        verify(authService, times(1)).registerUser(registrationDTO);
    }

    @Test
    public void testGetProfileByEmail() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setUserId(1L);
        user.setEmail(email);
        user.setName("Test User");

        when(authService.getUserProfile(email)).thenReturn(user);

        // Act
        ResponseEntity<User> responseEntity = authController.getProfile(email);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(authService, times(1)).getUserProfile(email);
    }

    @Test
    public void testGetUserProfile() throws FirebaseAuthException {
        // Arrange
        String authorizationHeader = "Bearer dummy_token";
        String token = "dummy_token";
        String email = "test@example.com";
        User user = new User();
        user.setUserId(1L);
        user.setEmail(email);
        user.setName("Test User");

        when(firebaseTokenValidator.validateTokenAndGetEmail(token)).thenReturn(email);
        when(authService.getUserProfile(email)).thenReturn(user);

        // Act
        ResponseEntity<User> responseEntity = authController.getUserProfile(authorizationHeader);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(firebaseTokenValidator, times(1)).validateTokenAndGetEmail(token);
        verify(authService, times(1)).getUserProfile(email);
    }
}