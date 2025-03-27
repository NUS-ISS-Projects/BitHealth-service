package com.bithealth.repositories;

import com.bithealth.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    Optional<Diagnosis> findByAppointment_AppointmentId(Long appointmentId);
}
