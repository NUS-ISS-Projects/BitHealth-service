package com.bithealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        prescription.setMedicines(dto.getMedicines()); // Convert list to comma-separated string
        prescription.setDosage(dto.getDosage());
        prescription.setDuration(dto.getDuration());
        prescription.setNotes(dto.getNotes());
        prescription.setIsVerified(false); // Default value

        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionByAppointment(Long appointmentId) {
        return prescriptionRepository.findByAppointment_AppointmentId(appointmentId);
    }

    public Prescription verifyPrescription(Long prescriptionId, PrescriptionVerificationDTO dto) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found with ID: " + prescriptionId));

        if (dto.getIsVerified() == null) {
            throw new IllegalArgumentException("isVerified parameter is required");
        }

        prescription.setIsVerified(dto.getIsVerified());
        return prescriptionRepository.save(prescription);
    }
}