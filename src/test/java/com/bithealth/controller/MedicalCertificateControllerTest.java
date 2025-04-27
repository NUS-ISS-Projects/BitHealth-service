package com.bithealth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bithealth.controllers.MedicalCertificateController;
import com.bithealth.dto.MedicalCertificateCreateRequestDTO;
import com.bithealth.dto.MedicalCertificateUpdateRequestDTO;
import com.bithealth.dto.MedicalCertificateVerificationDTO;
import com.bithealth.entities.MedicalCertificate;
import com.bithealth.services.MedicalCertificateService;

public class MedicalCertificateControllerTest {

    @InjectMocks
    private MedicalCertificateController medicalCertificateController;

    @Mock
    private MedicalCertificateService medicalCertificateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateMedicalCertificate() {
        // Arrange
        MedicalCertificateCreateRequestDTO createDTO = new MedicalCertificateCreateRequestDTO();
        createDTO.setAppointmentId(1L);
        createDTO.setCertificateNumber("MC-123");
        createDTO.setNoOfDays(5);
        createDTO.setEffectFrom(LocalDate.now());
        createDTO.setIssueDate(LocalDate.now());

        MedicalCertificate createdCertificate = new MedicalCertificate();
        createdCertificate.setCertificateId(1L);
        createdCertificate.setCertificateNumber("MC-123");

        when(medicalCertificateService.createMedicalCertificate(createDTO)).thenReturn(createdCertificate);

        // Act
        ResponseEntity<MedicalCertificate> responseEntity = medicalCertificateController.createMedicalCertificate(createDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdCertificate, responseEntity.getBody());
        verify(medicalCertificateService, times(1)).createMedicalCertificate(createDTO);
    }

    @Test
    public void testGetMedicalCertificateByAppointment() {
        // Arrange
        Long appointmentId = 1L;
        MedicalCertificate certificate = new MedicalCertificate();
        certificate.setCertificateId(1L);
        certificate.setCertificateNumber("MC-123");

        when(medicalCertificateService.getMedicalCertificateByAppointment(appointmentId)).thenReturn(certificate);

        // Act
        ResponseEntity<MedicalCertificate> responseEntity = medicalCertificateController.getMedicalCertificateByAppointment(appointmentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(certificate, responseEntity.getBody());
        verify(medicalCertificateService, times(1)).getMedicalCertificateByAppointment(appointmentId);
    }

    @Test
    public void testUpdateMedicalCertificate() {
        // Arrange
        Long certificateId = 1L;
        MedicalCertificateUpdateRequestDTO updateDTO = new MedicalCertificateUpdateRequestDTO();
        updateDTO.setNoOfDays(7);
        updateDTO.setEffectFrom(LocalDate.now().plusDays(1));
        updateDTO.setIssueDate(LocalDate.now());

        MedicalCertificate updatedCertificate = new MedicalCertificate();
        updatedCertificate.setCertificateId(certificateId);
        updatedCertificate.setNoOfDays(7);
        updatedCertificate.setEffectFrom(LocalDate.now().plusDays(1));
        updatedCertificate.setIssueDate(LocalDate.now());

        when(medicalCertificateService.updateMedicalCertificate(certificateId, updateDTO)).thenReturn(updatedCertificate);

        // Act
        ResponseEntity<MedicalCertificate> responseEntity = medicalCertificateController.updateMedicalCertificate(certificateId, updateDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCertificate, responseEntity.getBody());
        verify(medicalCertificateService, times(1)).updateMedicalCertificate(certificateId, updateDTO);
    }

    @Test
    public void testVerifyMedicalCertificate() {
        // Arrange
        Long certificateId = 1L;
        MedicalCertificateVerificationDTO verificationDTO = new MedicalCertificateVerificationDTO();
        verificationDTO.setLastVerified(LocalDateTime.now());

        MedicalCertificate verifiedCertificate = new MedicalCertificate();
        verifiedCertificate.setCertificateId(certificateId);
        verifiedCertificate.setLastVerified(verificationDTO.getLastVerified());
        verifiedCertificate.setIsVerified(true);

        when(medicalCertificateService.verifyMedicalCertificate(certificateId, verificationDTO)).thenReturn(verifiedCertificate);

        // Act
        ResponseEntity<MedicalCertificate> responseEntity = medicalCertificateController.verifyMedicalCertificate(certificateId, verificationDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(verifiedCertificate, responseEntity.getBody());
        verify(medicalCertificateService, times(1)).verifyMedicalCertificate(certificateId, verificationDTO);
    }
}