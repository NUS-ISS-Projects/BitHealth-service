package com.bithealth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bithealth.entities.MedicalCertificate;

public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {
    MedicalCertificate findByAppointment_AppointmentId(Long appointmentId);
}