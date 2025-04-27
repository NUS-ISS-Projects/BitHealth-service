package com.bithealth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bithealth.controllers.PatientController;
import com.bithealth.dto.PatientUpdateRequestDTO;
import com.bithealth.entities.Patient;
import com.bithealth.entities.User;
import com.bithealth.services.PatientService;

public class PatientControllerTest {

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientService patientService;

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
        patient.getUser().setName("Test Patient");

        when(patientService.getPatientProfile(patientId)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<Patient> responseEntity = patientController.getPatientProfile(patientId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patient, responseEntity.getBody());
        verify(patientService, times(1)).getPatientProfile(patientId);
    }

    @Test
    public void testGetPatientProfile_NotFound() {
        // Arrange
        Long patientId = 1L;
        when(patientService.getPatientProfile(patientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Patient> responseEntity = patientController.getPatientProfile(patientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(patientService, times(1)).getPatientProfile(patientId);
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

        Patient updatedPatient = new Patient();
        updatedPatient.setPatientId(patientId);
        User updatedUser = new User();
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@example.com");
        updatedPatient.setUser(updatedUser);
        updatedPatient.setAvatar("new_avatar_url");
        updatedPatient.setContactNumber("1234567890");
        updatedPatient.setDateOfBirth("1990-01-01");
        updatedPatient.setGender("Male");

        when(patientService.updatePatientProfile(patientId, requestDTO)).thenReturn(updatedPatient);

        // Act
        ResponseEntity<Patient> responseEntity = patientController.updatePatientProfile(patientId, requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedPatient, responseEntity.getBody());
        verify(patientService, times(1)).updatePatientProfile(patientId, requestDTO);
    }

    @Test
    public void testGetPatientProfileWithUser_Found() {
        // Arrange
        Long userId = 1L;
        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.getUser().setUserId(userId);
        patient.getUser().setName("Test Patient");

        when(patientService.getPatientProfileUserId(userId)).thenReturn(Optional.of(patient));

        // Act
        ResponseEntity<Patient> responseEntity = patientController.getPatientProfileWithUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patient, responseEntity.getBody());
        verify(patientService, times(1)).getPatientProfileUserId(userId);
    }

    @Test
    public void testGetPatientProfileWithUser_NotFound() {
        // Arrange
        Long userId = 1L;
        when(patientService.getPatientProfileUserId(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Patient> responseEntity = patientController.getPatientProfileWithUser(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(patientService, times(1)).getPatientProfileUserId(userId);
    }
}