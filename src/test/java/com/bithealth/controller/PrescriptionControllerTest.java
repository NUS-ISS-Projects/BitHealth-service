package com.bithealth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bithealth.controllers.PrescriptionController;
import com.bithealth.dto.MedicineItem;
import com.bithealth.dto.PresciptionUpdateRequestDTO;
import com.bithealth.dto.PrescriptionCreateRequestDTO;
import com.bithealth.dto.PrescriptionVerificationDTO;
import com.bithealth.entities.Prescription;
import com.bithealth.services.PrescriptionService;

public class PrescriptionControllerTest {

    @InjectMocks
    private PrescriptionController prescriptionController;

    @Mock
    private PrescriptionService prescriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePrescription() {
        // Arrange
        PrescriptionCreateRequestDTO createDTO = new PrescriptionCreateRequestDTO();
        createDTO.setAppointmentId(1L);
        createDTO.setInvoiceNo("INV-123");
        createDTO.setInvoiceDate(LocalDate.now());
        List<MedicineItem> medicineList = new ArrayList<>();
        MedicineItem item1 = new MedicineItem();
        item1.setMedicineName("Medicine A");
        item1.setDosage("10mg");
        medicineList.add(item1);
        createDTO.setMedicineList(medicineList);

        Prescription createdPrescription = new Prescription();
        createdPrescription.setPrescriptionId(1L);
        createdPrescription.setInvoiceNo("INV-123");

        when(prescriptionService.createPrescription(createDTO)).thenReturn(createdPrescription);

        // Act
        ResponseEntity<Prescription> responseEntity = prescriptionController.createPrescription(createDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdPrescription, responseEntity.getBody());
        verify(prescriptionService, times(1)).createPrescription(createDTO);
    }

    @Test
    public void testGetPrescriptionByAppointment() {
        // Arrange
        Long appointmentId = 1L;
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(1L);
        prescription.setInvoiceNo("INV-123");

        when(prescriptionService.getPrescriptionByAppointment(appointmentId)).thenReturn(prescription);

        // Act
        ResponseEntity<Prescription> responseEntity = prescriptionController.getPrescriptionByAppointment(appointmentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(prescription, responseEntity.getBody());
        verify(prescriptionService, times(1)).getPrescriptionByAppointment(appointmentId);
    }

    @Test
    public void testUpdatePrescription() {
        // Arrange
        Long prescriptionId = 1L;
        PresciptionUpdateRequestDTO updateDTO = new PresciptionUpdateRequestDTO();
         List<MedicineItem> medicineList = new ArrayList<>();
        MedicineItem item1 = new MedicineItem();
        item1.setMedicineName("Medicine B");
        item1.setDosage("20mg");
        medicineList.add(item1);
        updateDTO.setMedicineList(medicineList);
        updateDTO.setInvoiceDate(LocalDate.now().plusDays(1));


        Prescription updatedPrescription = new Prescription();
        updatedPrescription.setPrescriptionId(prescriptionId);
        updatedPrescription.setMedicineList(medicineList);
        updatedPrescription.setInvoiceDate(LocalDate.now().plusDays(1));

        when(prescriptionService.updatePrescription(prescriptionId, updateDTO)).thenReturn(updatedPrescription);

        // Act
        ResponseEntity<Prescription> responseEntity = prescriptionController.updatePrescription(prescriptionId, updateDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedPrescription, responseEntity.getBody());
        verify(prescriptionService, times(1)).updatePrescription(prescriptionId, updateDTO);
    }

    @Test
    public void testVerifyPrescription() {
        // Arrange
        Long prescriptionId = 1L;
        PrescriptionVerificationDTO verificationDTO = new PrescriptionVerificationDTO();
        verificationDTO.setLastVerified(LocalDateTime.now());

        Prescription verifiedPrescription = new Prescription();
        verifiedPrescription.setPrescriptionId(prescriptionId);
        verifiedPrescription.setLastVerified(verificationDTO.getLastVerified());
        verifiedPrescription.setIsVerified(true);

        when(prescriptionService.verifyPrescription(prescriptionId, verificationDTO)).thenReturn(verifiedPrescription);

        // Act
        ResponseEntity<Prescription> responseEntity = prescriptionController.verifyPrescription(prescriptionId, verificationDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(verifiedPrescription, responseEntity.getBody());
        verify(prescriptionService, times(1)).verifyPrescription(prescriptionId, verificationDTO);
    }
}