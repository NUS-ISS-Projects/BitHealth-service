package com.bithealth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bithealth.entities.Prescription;
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Prescription findByAppointment_AppointmentId(Long appointmentId);
}