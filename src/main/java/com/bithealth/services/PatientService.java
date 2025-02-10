package com.bithealth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.dto.PatientUpdateRequestDTO;
import com.bithealth.entities.Patient;
import com.bithealth.repositories.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Get Patient Profile
    public Optional<Patient> getPatientProfile(Long patientId) {
        return patientRepository.findById(patientId);
    }

    // Update Patient Profile
    public Patient updatePatientProfile(Long patientId, PatientUpdateRequestDTO request) {
        Patient patient = patientRepository.findById(patientId).orElseThrow();

        if (request.getDateOfBirth() != null) {
            patient.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            patient.setGender(request.getGender());
        }
        if (request.getMedicalHistory() != null) {
            patient.setMedicalHistory(request.getMedicalHistory());
        }

        return patientRepository.save(patient);
    }
}