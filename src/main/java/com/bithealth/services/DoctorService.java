package com.bithealth.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.dto.DoctorUpdateRequestDTO;
import com.bithealth.entities.Doctor;
import com.bithealth.entities.User;
import com.bithealth.repositories.DoctorRepository;
import com.bithealth.repositories.UserRepository;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAllWithUsers();
    }

    public Optional<Doctor> getDoctorProfile(Long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    public Optional<Doctor> getUserDoctorProfile(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return doctorRepository.findByUser(user);
    }

    // Update a doctor's profile
    public Doctor updateDoctorProfile(Long doctorId, DoctorUpdateRequestDTO request) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));

        User user = doctor.getUser();
        if (user != null) {
            if (request.getName() != null) {
                user.setName(request.getName());
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
        }

        if (request.getAvatar() != null) {
            doctor.setAvatar(request.getAvatar());
        }

        if (request.getSpecialization() != null) {
            doctor.setSpecialization(request.getSpecialization());
        }

        return doctorRepository.save(doctor);
    }
}