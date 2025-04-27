package com.bithealth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.bithealth.controllers.AppointmentController;
import com.bithealth.dto.AppointmentCreateRequestDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.services.AppointmentService;

class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = "PATIENT") // Simulate an authenticated patient
    void createAppointment_validInput_returns201Created() {
        // Arrange
        AppointmentCreateRequestDTO requestDTO = new AppointmentCreateRequestDTO();
        requestDTO.setPatientId(1L);
        requestDTO.setDoctorId(2L);
        requestDTO.setAppointmentDate(LocalDate.now());
        requestDTO.setAppointmentTime(LocalTime.now());
        requestDTO.setReasonForVisit("Checkup");
        requestDTO.setComment("Regular visit");

        Appointment createdAppointment = new Appointment(); // Create a mock Appointment object
        createdAppointment.setAppointmentId(101L);

        when(appointmentService.createAppointment(any(AppointmentCreateRequestDTO.class))).thenReturn(createdAppointment);

        // Act
        ResponseEntity<Appointment> responseEntity = appointmentController.createAppointment(null, requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdAppointment, responseEntity.getBody());
    }



    @Test
    @WithMockUser(roles = "PATIENT")
    void createAppointment_serviceThrowsException_propagatesToController() {
        // Arrange
        AppointmentCreateRequestDTO requestDTO = new AppointmentCreateRequestDTO();
        requestDTO.setPatientId(1L);
        requestDTO.setDoctorId(2L);
        requestDTO.setAppointmentDate(LocalDate.now());
        requestDTO.setAppointmentTime(LocalTime.now());
        requestDTO.setReasonForVisit("Checkup");
        requestDTO.setComment("Regular visit");

        when(appointmentService.createAppointment(any(AppointmentCreateRequestDTO.class)))
                .thenThrow(new RuntimeException("Failed to create appointment"));  // Use a more generic RuntimeException

        // Act & Assert
        assertThrows(RuntimeException.class, () -> { // Check for the RuntimeException
            appointmentController.createAppointment(null, requestDTO);
        });
    }
}