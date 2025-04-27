package com.bithealth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// MedicalCertificateService Test
import com.bithealth.dto.MedicalCertificateCreateRequestDTO;
import com.bithealth.dto.MedicalCertificateUpdateRequestDTO;
import com.bithealth.dto.MedicalCertificateVerificationDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.MedicalCertificate;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.MedicalCertificateRepository;
import com.bithealth.services.MedicalCertificateService;

public class MedicalCertificateServiceTest {

    @InjectMocks
    private MedicalCertificateService medicalCertificateService;

    @Mock
    private MedicalCertificateRepository medicalCertificateRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMedicalCertificate_Success() {
        // Arrange
        MedicalCertificateCreateRequestDTO createDTO = new MedicalCertificateCreateRequestDTO();
        createDTO.setAppointmentId(1L);
        createDTO.setCertificateNumber("MC-123");
        createDTO.setNoOfDays(5);
        createDTO.setEffectFrom(LocalDate.now());
        createDTO.setIssueDate(LocalDate.now());

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);

        MedicalCertificate savedCertificate = new MedicalCertificate();
        savedCertificate.setCertificateId(1L);
        savedCertificate.setCertificateNumber("MC-123");
        savedCertificate.setAppointment(appointment);
        savedCertificate.setNoOfDays(5);
        savedCertificate.setEffectFrom(LocalDate.now());
        savedCertificate.setIssueDate(LocalDate.now());
        savedCertificate.setIsVerified(false);


        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(medicalCertificateRepository.save(any(MedicalCertificate.class))).thenReturn(savedCertificate);

        // Act
        MedicalCertificate result = medicalCertificateService.createMedicalCertificate(createDTO);

        // Assert
        assertEquals(savedCertificate, result);
        verify(appointmentRepository, times(1)).findById(1L);
        verify(medicalCertificateRepository, times(1)).save(any(MedicalCertificate.class));
    }

    @Test
    public void testCreateMedicalCertificate_AppointmentNotFound() {
        // Arrange
        MedicalCertificateCreateRequestDTO createDTO = new MedicalCertificateCreateRequestDTO();
        createDTO.setAppointmentId(1L);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medicalCertificateService.createMedicalCertificate(createDTO);
        });
        assertEquals("Appointment not found with ID: 1", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(1L);
        verify(medicalCertificateRepository, never()).save(any(MedicalCertificate.class));
    }

    @Test
    public void testUpdateMedicalCertificate_Success() {
        // Arrange
        Long certificateId = 1L;
        MedicalCertificateUpdateRequestDTO updateDTO = new MedicalCertificateUpdateRequestDTO();
        updateDTO.setNoOfDays(7);
        updateDTO.setEffectFrom(LocalDate.now().plusDays(1));
        updateDTO.setIssueDate(LocalDate.now());

        MedicalCertificate existingCertificate = new MedicalCertificate();
        existingCertificate.setCertificateId(certificateId);
        existingCertificate.setNoOfDays(5);
        existingCertificate.setEffectFrom(LocalDate.now());
        existingCertificate.setIssueDate(LocalDate.now());
        existingCertificate.setIsVerified(true);

        MedicalCertificate updatedCertificate = new MedicalCertificate();
        updatedCertificate.setCertificateId(certificateId);
        updatedCertificate.setNoOfDays(7);
        updatedCertificate.setEffectFrom(LocalDate.now().plusDays(1));
        updatedCertificate.setIssueDate(LocalDate.now());
        updatedCertificate.setIsVerified(false);

        when(medicalCertificateRepository.findById(certificateId)).thenReturn(Optional.of(existingCertificate));
        when(medicalCertificateRepository.save(any(MedicalCertificate.class))).thenReturn(updatedCertificate);

        // Act
        MedicalCertificate result = medicalCertificateService.updateMedicalCertificate(certificateId, updateDTO);

        // Assert
        assertEquals(updatedCertificate, result);
        verify(medicalCertificateRepository, times(1)).findById(certificateId);
        verify(medicalCertificateRepository, times(1)).save(any(MedicalCertificate.class));
    }

    @Test
    public void testUpdateMedicalCertificate_NotFound() {
        // Arrange
        Long certificateId = 1L;
        MedicalCertificateUpdateRequestDTO updateDTO = new MedicalCertificateUpdateRequestDTO();
        updateDTO.setNoOfDays(7);

        when(medicalCertificateRepository.findById(certificateId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medicalCertificateService.updateMedicalCertificate(certificateId, updateDTO);
        });
        assertEquals("Medical Certificate not found with ID: 1", exception.getMessage());
        verify(medicalCertificateRepository, times(1)).findById(certificateId);
        verify(medicalCertificateRepository, never()).save(any(MedicalCertificate.class));
    }

    @Test
    public void testGetMedicalCertificateByAppointment() {
        // Arrange
        Long appointmentId = 1L;
        MedicalCertificate certificate = new MedicalCertificate();
        certificate.setCertificateId(1L);
        certificate.setCertificateNumber("MC-123");
        certificate.setAppointment(new Appointment());

        when(medicalCertificateRepository.findByAppointment_AppointmentId(appointmentId)).thenReturn(certificate);

        // Act
        MedicalCertificate result = medicalCertificateService.getMedicalCertificateByAppointment(appointmentId);

        // Assert
        assertEquals(certificate, result);
        verify(medicalCertificateRepository, times(1)).findByAppointment_AppointmentId(appointmentId);
    }

    @Test
    public void testVerifyMedicalCertificate_Success() {
        // Arrange
        Long certificateId = 1L;
        MedicalCertificateVerificationDTO verificationDTO = new MedicalCertificateVerificationDTO();
        LocalDateTime verificationTime = LocalDateTime.now();
        verificationDTO.setLastVerified(verificationTime);

        MedicalCertificate existingCertificate = new MedicalCertificate();
        existingCertificate.setCertificateId(certificateId);
        existingCertificate.setIsVerified(false);

        MedicalCertificate verifiedCertificate = new MedicalCertificate();
        verifiedCertificate.setCertificateId(certificateId);
        verifiedCertificate.setIsVerified(true);
        verifiedCertificate.setLastVerified(verificationTime);


        when(medicalCertificateRepository.findById(certificateId)).thenReturn(Optional.of(existingCertificate));
        when(medicalCertificateRepository.save(any(MedicalCertificate.class))).thenReturn(verifiedCertificate);

        // Act
        MedicalCertificate result = medicalCertificateService.verifyMedicalCertificate(certificateId, verificationDTO);

        // Assert
        assertEquals(verifiedCertificate, result);
        verify(medicalCertificateRepository, times(1)).findById(certificateId);
        verify(medicalCertificateRepository, times(1)).save(any(MedicalCertificate.class));
    }

    @Test
    public void testVerifyMedicalCertificate_NotFound() {
        // Arrange
        Long certificateId = 1L;
        MedicalCertificateVerificationDTO verificationDTO = new MedicalCertificateVerificationDTO();
        verificationDTO.setLastVerified(LocalDateTime.now());

        when(medicalCertificateRepository.findById(certificateId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medicalCertificateService.verifyMedicalCertificate(certificateId, verificationDTO);
        });
        assertEquals("Medical certificate not found with ID: 1", exception.getMessage());
        verify(medicalCertificateRepository, times(1)).findById(certificateId);
        verify(medicalCertificateRepository, never()).save(any(MedicalCertificate.class));
    }
}
