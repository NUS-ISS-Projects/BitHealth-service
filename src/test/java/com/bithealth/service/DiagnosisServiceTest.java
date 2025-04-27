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

import com.bithealth.dto.DiagnosisUpdateDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Diagnosis;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.DiagnosisRepository;
import com.bithealth.services.DiagnosisService;

public class DiagnosisServiceTest {

    @InjectMocks
    private DiagnosisService diagnosisService;

    @Mock
    private DiagnosisRepository diagnosisRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrUpdateDiagnosis_NewDiagnosis() {
        // Arrange
        Long appointmentId = 1L;
        DiagnosisUpdateDTO dto = new DiagnosisUpdateDTO();
        dto.setDiagnosis("Common Cold");
     

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);

        Diagnosis savedDiagnosis = new Diagnosis();
        savedDiagnosis.setDiagnosis("Common Cold");
    
        savedDiagnosis.setAppointment(appointment);


        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(diagnosisRepository.findByAppointment_AppointmentId(appointmentId)).thenReturn(Optional.empty());
        when(diagnosisRepository.save(any(Diagnosis.class))).thenReturn(savedDiagnosis);

        // Act
        Diagnosis result = diagnosisService.addOrUpdateDiagnosis(appointmentId, dto);

        // Assert
        assertEquals(savedDiagnosis, result);
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(diagnosisRepository, times(1)).findByAppointment_AppointmentId(appointmentId);
        verify(diagnosisRepository, times(1)).save(any(Diagnosis.class));
    }

    @Test
    public void testAddOrUpdateDiagnosis_ExistingDiagnosis() {
        // Arrange
        Long appointmentId = 1L;
        DiagnosisUpdateDTO dto = new DiagnosisUpdateDTO();
        dto.setDiagnosis("Updated: Common Cold");
  
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);

        Diagnosis existingDiagnosis = new Diagnosis();
        existingDiagnosis.setDiagnosis("Common Cold");
 
        existingDiagnosis.setAppointment(appointment);

        Diagnosis updatedDiagnosis = new Diagnosis();
        updatedDiagnosis.setDiagnosis("Updated: Common Cold");

        updatedDiagnosis.setAppointment(appointment);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(diagnosisRepository.findByAppointment_AppointmentId(appointmentId)).thenReturn(Optional.of(existingDiagnosis));
        when(diagnosisRepository.save(any(Diagnosis.class))).thenReturn(updatedDiagnosis);

        // Act
        Diagnosis result = diagnosisService.addOrUpdateDiagnosis(appointmentId, dto);

        // Assert
        assertEquals(updatedDiagnosis, result);
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(diagnosisRepository, times(1)).findByAppointment_AppointmentId(appointmentId);
        verify(diagnosisRepository, times(1)).save(any(Diagnosis.class));
    }

    @Test
    public void testAddOrUpdateDiagnosis_AppointmentNotFound() {
        // Arrange
        Long appointmentId = 1L;
        DiagnosisUpdateDTO dto = new DiagnosisUpdateDTO();
        dto.setDiagnosis("Common Cold");


        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diagnosisService.addOrUpdateDiagnosis(appointmentId, dto);
        });
        assertEquals("Appointment not found: 1", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(diagnosisRepository, never()).findByAppointment_AppointmentId(appointmentId);
        verify(diagnosisRepository, never()).save(any(Diagnosis.class));
    }

    @Test
    public void testGetDiagnosisByAppointment_Found() {
        // Arrange
        Long appointmentId = 1L;
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosis("Common Cold");

        when(diagnosisRepository.findByAppointment_AppointmentId(appointmentId)).thenReturn(Optional.of(diagnosis));

        // Act
        Diagnosis result = diagnosisService.getDiagnosisByAppointment(appointmentId);

        // Assert
        assertEquals(diagnosis, result);
        verify(diagnosisRepository, times(1)).findByAppointment_AppointmentId(appointmentId);
    }

    @Test
    public void testGetDiagnosisByAppointment_NotFound() {
        // Arrange
        Long appointmentId = 1L;
        when(diagnosisRepository.findByAppointment_AppointmentId(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            diagnosisService.getDiagnosisByAppointment(appointmentId);
        });
        assertEquals("Diagnosis not found for appointment with ID: 1", exception.getMessage());
        verify(diagnosisRepository, times(1)).findByAppointment_AppointmentId(appointmentId);
    }
}