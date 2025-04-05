package com.bithealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.dto.MedicalCertificateCreateRequestDTO;
import com.bithealth.dto.MedicalCertificateVerificationDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.MedicalCertificate;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.MedicalCertificateRepository;

@Service
public class MedicalCertificateService {
    @Autowired
    private MedicalCertificateRepository medicalCertificateRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public MedicalCertificate createMedicalCertificate(MedicalCertificateCreateRequestDTO dto) {
        // Fetch the associated Appointment
        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Appointment not found with ID: " + dto.getAppointmentId()));

        // Create the MedicalCertificate
        MedicalCertificate certificate = new MedicalCertificate();
        certificate.setAppointment(appointment);
        certificate.setCertificateNumber(dto.getCertificateNumber());
        certificate.setNoOfDays(dto.getNoOfDays());
        certificate.setEffectFrom(dto.getEffectFrom());
        certificate.setIssueDate(dto.getIssueDate());
        certificate.setIsVerified(false);

        return medicalCertificateRepository.save(certificate);
    }

    public MedicalCertificate getMedicalCertificateByAppointment(Long appointmentId) {
        return medicalCertificateRepository.findByAppointment_AppointmentId(appointmentId);
    }

    public MedicalCertificate verifyMedicalCertificate(Long certificateId, MedicalCertificateVerificationDTO dto) {
        MedicalCertificate certificate = medicalCertificateRepository.findById(certificateId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Medical certificate not found with ID: " + certificateId));
        //TODO: Do a check here before they are allow to set value as True
        certificate.setLastVerified(dto.getLastVerified());
        certificate.setIsVerified(true);
        return medicalCertificateRepository.save(certificate);
    }
}
