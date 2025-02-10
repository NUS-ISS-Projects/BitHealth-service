package com.bithealth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bithealth.entities.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Prescription findByAppointment_AppointmentId(Long appointmentId);
}