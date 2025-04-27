package com.bithealth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bithealth.dto.MedicineItem;
// PrescriptionService Test
import com.bithealth.dto.PresciptionUpdateRequestDTO;
import com.bithealth.dto.PrescriptionCreateRequestDTO;
import com.bithealth.dto.PrescriptionVerificationDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Prescription;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.PrescriptionRepository;
import com.bithealth.services.PrescriptionService;

public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePrescription_Success() {
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

        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);

        Prescription savedPrescription = new Prescription();
        savedPrescription.setPrescriptionId(1L);
        savedPrescription.setInvoiceNo("INV-123");
        savedPrescription.setAppointment(appointment);
        savedPrescription.setMedicineList(medicineList);
        savedPrescription.setInvoiceDate(LocalDate.now());
        savedPrescription.setIsVerified(false);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(savedPrescription);

        // Act
        Prescription result = prescriptionService.createPrescription(createDTO);

        // Assert
        assertEquals(savedPrescription, result);
        verify(appointmentRepository, times(1)).findById(1L);
        verify(prescriptionRepository, times(1)).save(any(Prescription.class));
    }

    @Test
    public void testCreatePrescription_AppointmentNotFound() {
        // Arrange
        PrescriptionCreateRequestDTO createDTO = new PrescriptionCreateRequestDTO();
        createDTO.setAppointmentId(1L);

        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            prescriptionService.createPrescription(createDTO);
        });
        assertEquals("Appointment not found with ID: 1", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(1L);
        verify(prescriptionRepository, never()).save(any(Prescription.class));
    }

    @Test
    public void testUpdatePrescription_Success() {
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

        Prescription existingPrescription = new Prescription();
        existingPrescription.setPrescriptionId(prescriptionId);
        existingPrescription.setInvoiceNo("INV-123");
        existingPrescription.setInvoiceDate(LocalDate.now());
        existingPrescription.setMedicineList(new ArrayList<>());
        existingPrescription.setIsVerified(true);

        Prescription updatedPrescription = new Prescription();
        updatedPrescription.setPrescriptionId(prescriptionId);
        updatedPrescription.setMedicineList(medicineList);
        updatedPrescription.setInvoiceDate(LocalDate.now().plusDays(1));
        updatedPrescription.setIsVerified(false);

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(existingPrescription));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(updatedPrescription);

        // Act
        Prescription result = prescriptionService.updatePrescription(prescriptionId, updateDTO);

        // Assert
        assertEquals(updatedPrescription, result);
        verify(prescriptionRepository, times(1)).findById(prescriptionId);
        verify(prescriptionRepository, times(1)).save(any(Prescription.class));
    }

    @Test
    public void testUpdatePrescription_NotFound() {
        // Arrange
        Long prescriptionId = 1L;
        PresciptionUpdateRequestDTO updateDTO = new PresciptionUpdateRequestDTO();
        updateDTO.setInvoiceDate(LocalDate.now());

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            prescriptionService.updatePrescription(prescriptionId, updateDTO);
        });
        assertEquals("Prescription not found with ID: 1", exception.getMessage());
        verify(prescriptionRepository, times(1)).findById(prescriptionId);
        verify(prescriptionRepository, never()).save(any(Prescription.class));
    }

    @Test
    public void testGetPrescriptionByAppointment() {
        // Arrange
        Long appointmentId = 1L;
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(1L);
        prescription.setInvoiceNo("INV-123");
        prescription.setAppointment(new Appointment());

        when(prescriptionRepository.findByAppointment_AppointmentId(appointmentId)).thenReturn(prescription);

        // Act
        Prescription result = prescriptionService.getPrescriptionByAppointment(appointmentId);

        // Assert
        assertEquals(prescription, result);
        verify(prescriptionRepository, times(1)).findByAppointment_AppointmentId(appointmentId);
    }

    @Test
    public void testVerifyPrescription_Success() {
        // Arrange
        Long prescriptionId = 1L;
        PrescriptionVerificationDTO verificationDTO = new PrescriptionVerificationDTO();
        LocalDateTime verificationTime = LocalDateTime.now();
        verificationDTO.setLastVerified(verificationTime);

        Prescription existingPrescription = new Prescription();
        existingPrescription.setPrescriptionId(prescriptionId);
        existingPrescription.setIsVerified(false);

        Prescription verifiedPrescription = new Prescription();
        verifiedPrescription.setPrescriptionId(prescriptionId);
        verifiedPrescription.setIsVerified(true);
        verifiedPrescription.setLastVerified(verificationTime);

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.of(existingPrescription));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(verifiedPrescription);

        // Act
        Prescription result = prescriptionService.verifyPrescription(prescriptionId, verificationDTO);

        // Assert
        assertEquals(verifiedPrescription, result);
        verify(prescriptionRepository, times(1)).findById(prescriptionId);
        verify(prescriptionRepository, times(1)).save(any(Prescription.class));
    }

    @Test
    public void testVerifyPrescription_NotFound() {
        // Arrange
        Long prescriptionId = 1L;
        PrescriptionVerificationDTO verificationDTO = new PrescriptionVerificationDTO();
        verificationDTO.setLastVerified(LocalDateTime.now());

        when(prescriptionRepository.findById(prescriptionId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            prescriptionService.verifyPrescription(prescriptionId, verificationDTO);
        });
        assertEquals("Prescription not found with ID: 1", exception.getMessage());
        verify(prescriptionRepository, times(1)).findById(prescriptionId);
        verify(prescriptionRepository, never()).save(any(Prescription.class));
    }
}
