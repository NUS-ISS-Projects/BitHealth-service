package com.bithealth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bithealth.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findById(Long patientId);
    @Query("SELECT p FROM Patient p WHERE p.user.userId = :userId")
    Optional<Patient> findByUserId(Long userId);
}
