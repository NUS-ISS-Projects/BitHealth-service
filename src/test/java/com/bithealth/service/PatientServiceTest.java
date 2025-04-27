package com.bithealth.service;

// PatientService Test
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bithealth.dto.PatientUpdateRequestDTO;
import com.bithealth.entities.Patient;
import com.bithealth.entities.User;
import com.bithealth.repositories.PatientRepository;
import com.bithealth.services.PatientService;

public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientProfile_Found() {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient();
        patient.setPatientId(patientId);

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientService.getPatientProfile(patientId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    public void testGetPatientProfile_NotFound() {
        // Arrange
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientService.getPatientProfile(patientId);

        // Assert
        assertFalse(result.isPresent());
        verify(patientRepository, times(1)).findById(patientId);
    }

    @Test
    public void testUpdatePatientProfile() {
        // Arrange
        Long patientId = 1L;
        PatientUpdateRequestDTO requestDTO = new PatientUpdateRequestDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setEmail("updated@example.com");
        requestDTO.setAvatar("new_avatar_url");
        requestDTO.setContact_number("1234567890");
        requestDTO.setDateOfBirth("1990-01-01");
        requestDTO.setGender("Male");

        Patient existingPatient = new Patient();
        existingPatient.setPatientId(patientId);
        User existingUser = new User();
        existingUser.setName("Original Name");
        existingUser.setEmail("original@example.com");
        existingPatient.setUser(existingUser);
        existingPatient.setAvatar("original_avatar_url");
        existingPatient.setContactNumber("0987654321");
        existingPatient.setDateOfBirth("1990-05-05");
        existingPatient.setGender("Female");

        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        // Act
        Patient updatedPatient = patientService.updatePatientProfile(patientId, requestDTO);

        // Assert
        assertEquals(patientId, updatedPatient.getPatientId());
        assertEquals("Updated Name", updatedPatient.getUser().getName());
        assertEquals("updated@example.com", updatedPatient.getUser().getEmail());
        assertEquals("new_avatar_url", updatedPatient.getAvatar());
        assertEquals("1234567890", updatedPatient.getContactNumber());
        assertEquals("1990-01-01", updatedPatient.getDateOfBirth());
        assertEquals("Male", updatedPatient.getGender());
        verify(patientRepository, times(1)).findById(patientId);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    public void testGetPatientProfileUserId_Found() {
        // Arrange
        Long userId = 1L;
        Patient patient = new Patient();
        patient.setPatientId(1L);
        User user = new User();
        user.setUserId(userId);
        patient.setUser(user);

        when(patientRepository.findByUserId(userId)).thenReturn(Optional.of(patient));

        // Act
        Optional<Patient> result = patientService.getPatientProfileUserId(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
        verify(patientRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetPatientProfileUserId_NotFound() {
        // Arrange
        Long userId = 1L;
        when(patientRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientService.getPatientProfileUserId(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(patientRepository, times(1)).findByUserId(userId);
    }
}

