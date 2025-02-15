package com.bithealth.repositories;

import com.bithealth.entities.Patient;
import com.bithealth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);

    Optional<Patient> findById(Long patientId);
}
