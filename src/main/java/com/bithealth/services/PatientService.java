package com.bithealth.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.dto.PatientUpdateRequestDTO;
import com.bithealth.entities.Patient;
import com.bithealth.entities.User;
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

        User user = patient.getUser();
        if (user != null) {
            if (request.getName() != null) {
                user.setName(request.getName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
        }

        if (request.getAvatar() != null) {
            patient.setAvatar(request.getAvatar());
        }
        if (request.getContact_number() != null) {
            patient.setContactNumber(request.getContact_number());
        }
        if (request.getDateOfBirth() != null) {
            patient.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            patient.setGender(request.getGender());
        }
        return patientRepository.save(patient);
    }

    public Optional<Patient> getPatientProfileUserId(Long userId) {
        // TODO Auto-generated method stub
        return patientRepository.findByUserId(userId);    
    }

    
}