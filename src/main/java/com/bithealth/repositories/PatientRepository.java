package com.bithealth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bithealth.entities.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bithealth.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findById(Long patientId);
}
