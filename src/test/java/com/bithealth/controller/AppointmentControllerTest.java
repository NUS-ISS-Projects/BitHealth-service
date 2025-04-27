package com.bithealth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

import com.bithealth.controllers.AppointmentController;
import com.bithealth.dto.AppointmentCreateRequestDTO;
import com.bithealth.dto.AppointmentRescheduleDTO;
import com.bithealth.dto.AppointmentStatusUpdateDTO;
import com.bithealth.dto.DiagnosisUpdateDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Diagnosis;
import com.bithealth.entities.Doctor;
import com.bithealth.entities.Patient;
import com.bithealth.entities.User;
import com.bithealth.services.AppointmentService;
import com.bithealth.services.DiagnosisService;

public class AppointmentControllerTest {

    @InjectMocks
    private AppointmentController appointmentController;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private DiagnosisService diagnosisService;

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
    public void testCreateAppointment() {
        // Arrange
        AppointmentCreateRequestDTO requestDTO = new AppointmentCreateRequestDTO();
        requestDTO.setPatientId(1L);
        requestDTO.setDoctorId(2L);
        requestDTO.setAppointmentDate(LocalDate.now());
        requestDTO.setAppointmentTime(LocalTime.now());
        requestDTO.setReasonForVisit("Checkup");
        requestDTO.setComment("Follow-up");

        Appointment createdAppointment = new Appointment();
        createdAppointment.setAppointmentId(1L);

        when(appointmentService.createAppointment(requestDTO)).thenReturn(createdAppointment);

        // Act
        ResponseEntity<Appointment> responseEntity = appointmentController.createAppointment("Bearer dummy_token", requestDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdAppointment, responseEntity.getBody());
        verify(appointmentService, times(1)).createAppointment(requestDTO);
    }

    @Test
    public void testGetAvailableAppointmentsForDoctor() {
        // Arrange
        User mockUser = new User();
        Doctor mockDoctor = new Doctor();
        mockDoctor.setDoctorId(101L);
        mockUser.setDoctor(mockDoctor);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        List<Appointment> availableAppointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentId(2L);
        availableAppointments.add(appointment1);
        availableAppointments.add(appointment2);

        when(appointmentService.getAvailableAppointmentsForDoctor(101L)).thenReturn(availableAppointments);

        // Act
        ResponseEntity<List<Appointment>> responseEntity = appointmentController.getAvailableAppointmentsForDoctor();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(availableAppointments, responseEntity.getBody());
        verify(appointmentService, times(1)).getAvailableAppointmentsForDoctor(101L);
    }

    @Test
    public void testGetAppointmentsForPatient() {
        // Arrange
        User mockUser = new User();
        Patient mockPatient = new Patient();
        mockPatient.setPatientId(201L);
        mockUser.setPatient(mockPatient);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        List<Appointment> patientAppointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(3L);
        patientAppointments.add(appointment1);

        when(appointmentService.getAppointmentsForPatient(201L)).thenReturn(patientAppointments);

        // Act
        ResponseEntity<List<Appointment>> responseEntity = appointmentController.getAppointmentsForPatient();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(patientAppointments, responseEntity.getBody());
        verify(appointmentService, times(1)).getAppointmentsForPatient(201L);
    }

    @Test
    public void testGetAppointmentById() {
        // Arrange
        Long appointmentId = 1L;
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);

        when(appointmentService.getAppointmentById(appointmentId)).thenReturn(appointment);

        // Act
        ResponseEntity<Appointment> responseEntity = appointmentController.getAppointmentById(appointmentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appointment, responseEntity.getBody());
        verify(appointmentService, times(1)).getAppointmentById(appointmentId);
    }

    @Test
    public void testDeleteAppointment() {
        // Arrange
        Long appointmentId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = appointmentController.deleteAppointment(appointmentId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(appointmentService, times(1)).deleteAppointment(appointmentId);
    }

    @Test
    public void testUpdateAppointmentDateTime() {
        // Arrange
        Long appointmentId = 1L;
        AppointmentRescheduleDTO rescheduleDTO = new AppointmentRescheduleDTO();
        rescheduleDTO.setAppointmentDate(LocalDate.now().plusDays(1));
        rescheduleDTO.setAppointmentTime(LocalTime.now().plusHours(1));

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setAppointmentId(appointmentId);
        updatedAppointment.setAppointmentDate(rescheduleDTO.getAppointmentDate());
        updatedAppointment.setAppointmentTime(rescheduleDTO.getAppointmentTime());

        when(appointmentService.updateAppointmentDateTime(appointmentId, rescheduleDTO.getAppointmentDate(), rescheduleDTO.getAppointmentTime()))
                .thenReturn(updatedAppointment);

        // Act
        ResponseEntity<Appointment> responseEntity = appointmentController.updateAppointmentDateTime(appointmentId, rescheduleDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedAppointment, responseEntity.getBody());
        verify(appointmentService, times(1)).updateAppointmentDateTime(appointmentId, rescheduleDTO.getAppointmentDate(), rescheduleDTO.getAppointmentTime());
    }

    @Test
    public void testUpdateAppointmentStatus() {
        // Arrange
        Long appointmentId = 1L;
        AppointmentStatusUpdateDTO statusUpdateDTO = new AppointmentStatusUpdateDTO();
        statusUpdateDTO.setStatus("CANCELLED");

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setAppointmentId(appointmentId);
        updatedAppointment.setStatus(Appointment.Status.CANCELLED);

        when(appointmentService.updateAppointmentStatus(appointmentId, statusUpdateDTO.getStatus()))
                .thenReturn(updatedAppointment);

        // Act
        ResponseEntity<Appointment> responseEntity = appointmentController.updateAppointmentStatus(appointmentId, statusUpdateDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedAppointment, responseEntity.getBody());
        verify(appointmentService, times(1)).updateAppointmentStatus(appointmentId, statusUpdateDTO.getStatus());
    }

    @Test
    public void testUpdateDiagnosis() {
        // Arrange
        Long appointmentId = 1L;
        DiagnosisUpdateDTO diagnosisUpdateDTO = new DiagnosisUpdateDTO();
        diagnosisUpdateDTO.setDiagnosis("Common Cold");
        diagnosisUpdateDTO.setPrescription("Rest and fluids");

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1L);
        diagnosis.setDiagnosis("Common Cold");
        diagnosis.setPrescription("Rest and fluids");

        when(diagnosisService.addOrUpdateDiagnosis(appointmentId, diagnosisUpdateDTO)).thenReturn(diagnosis);

        //Act
        ResponseEntity<Diagnosis> responseEntity = appointmentController.updateDiagnosis(appointmentId, diagnosisUpdateDTO);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(diagnosis, responseEntity.getBody());
        verify(diagnosisService, times(1)).addOrUpdateDiagnosis(appointmentId, diagnosisUpdateDTO);
    }

    @Test
    public void testGetDiagnosisByAppointment() {
        //Arrange
        Long appointmentId = 1L;
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosisId(1L);
        diagnosis.setDiagnosis("Common Cold");
        diagnosis.setPrescription("Rest and fluids");

        when(diagnosisService.getDiagnosisByAppointment(appointmentId)).thenReturn(diagnosis);

        //Act
        ResponseEntity<Diagnosis> responseEntity = appointmentController.getDiagnosisByAppointment(appointmentId);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(diagnosis, responseEntity.getBody());
        verify(diagnosisService, times(1)).getDiagnosisByAppointment(appointmentId);
    }

    @Test
    public void testGetAllAppointments() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setAppointmentId(2L);
        appointments.add(appointment1);
        appointments.add(appointment2);

        when(appointmentService.getAllAppointments()).thenReturn(appointments);

        // Act
        ResponseEntity<List<Appointment>> responseEntity = appointmentController.getAllAppointments();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appointments, responseEntity.getBody());
        verify(appointmentService, times(1)).getAllAppointments();
    }
}
