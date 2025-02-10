package com.bithealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.entities.Prescription;
import com.bithealth.repositories.PrescriptionRepository;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Prescription getPrescriptionByAppointment(Long appointmentId) {
        return prescriptionRepository.findByAppointment_AppointmentId(appointmentId);
    }

    public Prescription verifyPrescription(Long prescriptionId, Boolean isVerified) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId).orElseThrow();
        prescription.setIsVerified(isVerified);
        return prescriptionRepository.save(prescription);
    }
}