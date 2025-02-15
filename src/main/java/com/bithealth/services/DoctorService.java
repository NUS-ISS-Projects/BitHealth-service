package com.bithealth.services;

import com.bithealth.dto.DoctorUpdateRequestDTO;
import com.bithealth.entities.Doctor;
import com.bithealth.repositories.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorProfile(Long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    // Update a doctor's profile
    public Doctor updateDoctorProfile(Long doctorId, DoctorUpdateRequestDTO request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));

        if (request.getSpecialization() != null) {
            doctor.setSpecialization(request.getSpecialization());
        }
        if (request.getBio() != null) {
            doctor.setBio(request.getBio());
        }

        return doctorRepository.save(doctor);
    }
}