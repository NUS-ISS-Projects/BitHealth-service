package com.bithealth.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.dto.PresciptionUpdateRequestDTO;
import com.bithealth.dto.PrescriptionCreateRequestDTO;
import com.bithealth.dto.PrescriptionVerificationDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Prescription;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.PrescriptionRepository;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Prescription createPrescription(PrescriptionCreateRequestDTO dto) {
        // Fetch the associated Appointment
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Appointment not found with ID: " + dto.getAppointmentId()));

        // Create the Prescription
        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setMedicineList(dto.getMedicineList());
        prescription.setInvoiceNo(dto.getInvoiceNo());
        prescription.setInvoiceDate(dto.getInvoiceDate());
        //TODO: Do a check here before they are allow to set value as True
        prescription.setIsVerified(false);

        return prescriptionRepository.save(prescription);
    }

    public Prescription updatePrescription(Long prescriptionId, PresciptionUpdateRequestDTO dto) {
        Prescription existingPrescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found with ID: " + prescriptionId));
        existingPrescription.setMedicineList(dto.getMedicineList());
        existingPrescription.setInvoiceDate(dto.getInvoiceDate());
        existingPrescription.setIsVerified(false);
        return prescriptionRepository.save(existingPrescription);
    }

    public Prescription getPrescriptionByAppointment(Long appointmentId) {
        return prescriptionRepository.findByAppointment_AppointmentId(appointmentId);
    }

    public Prescription verifyPrescription(Long prescriptionId, PrescriptionVerificationDTO dto) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found with ID: " + prescriptionId));
        //TODO: Do a check here before they are allow to set value as True
        prescription.setLastVerified(dto.getLastVerified());
        prescription.setIsVerified(true);
        return prescriptionRepository.save(prescription);
    }
}