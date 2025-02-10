package com.bithealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.entities.MedicalCertificate;
import com.bithealth.repositories.MedicalCertificateRepository;

@Service
public class MedicalCertificateService {
    @Autowired
    private MedicalCertificateRepository medicalCertificateRepository;

    public MedicalCertificate createMedicalCertificate(MedicalCertificate certificate) {
        return medicalCertificateRepository.save(certificate);
    }

    public MedicalCertificate getMedicalCertificateByAppointment(Long appointmentId) {
        return medicalCertificateRepository.findByAppointment_AppointmentId(appointmentId);
    }

    public MedicalCertificate verifyMedicalCertificate(Long certificateId, Boolean isVerified) {
        MedicalCertificate certificate = medicalCertificateRepository.findById(certificateId).orElseThrow();
        certificate.setIsVerified(isVerified);
        return medicalCertificateRepository.save(certificate);
    }
}
