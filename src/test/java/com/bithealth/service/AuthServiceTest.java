package com.bithealth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bithealth.dto.UserRegistrationDTO;
import com.bithealth.entities.User;
import com.bithealth.entities.User.Role;
import com.bithealth.repositories.DoctorRepository;
import com.bithealth.repositories.PatientRepository;
import com.bithealth.repositories.UserRepository;
import com.bithealth.services.AuthService;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Patient() {
        // Arrange
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("Test Patient");
        registrationDTO.setEmail("patient@example.com");
        registrationDTO.setRole("PATIENT");
        registrationDTO.setFirebaseUid("patient_uid");

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setName("Test Patient");
        savedUser.setEmail("patient@example.com");
        savedUser.setRole(Role.PATIENT);
        savedUser.setFirebaseUid("patient_uid");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User registeredUser = authService.registerUser(registrationDTO);

        // Assert
        assertEquals(savedUser, registeredUser);
        verify(userRepository, times(1)).save(any(User.class));
        verify(patientRepository, times(1)).save(any()); // Ensure patientRepository.save is called
        verify(doctorRepository, never()).save(any());
    }

    @Test
    public void testRegisterUser_Doctor() {
        // Arrange
        UserRegistrationDTO registrationDTO = new UserRegistrationDTO();
        registrationDTO.setName("Test Doctor");
        registrationDTO.setEmail("doctor@example.com");
        registrationDTO.setRole("DOCTOR");
        registrationDTO.setFirebaseUid("doctor_uid");

        User savedUser = new User();
        savedUser.setUserId(2L);
        savedUser.setName("Test Doctor");
        savedUser.setEmail("doctor@example.com");
        savedUser.setRole(Role.DOCTOR);
        savedUser.setFirebaseUid("doctor_uid");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User registeredUser = authService.registerUser(registrationDTO);

        // Assert
        assertEquals(savedUser, registeredUser);
        verify(userRepository, times(1)).save(any(User.class));
        verify(doctorRepository, times(1)).save(any()); // Ensure doctorRepository.save is called
        verify(patientRepository, never()).save(any());
    }

    @Test
    public void testGetUserProfile_Success() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setUserId(1L);
        user.setEmail(email);
        user.setName("Test User");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User foundUser = authService.getUserProfile(email);

        // Assert
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testGetUserProfile_NotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.getUserProfile(email);
        });
        assertEquals("User not found with email: " + email, exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
    }
}

