package com.bithealth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bithealth.entities.MedicalCertificate;
@Repository
public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {
    MedicalCertificate findByAppointment_AppointmentId(Long appointmentId);
}