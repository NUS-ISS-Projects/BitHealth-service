package com.bithealth.services;

import com.bithealth.dto.DoctorUpdateRequestDTO;
import com.bithealth.entities.Doctor;
<<<<<<< HEAD
=======
import com.bithealth.entities.User;
>>>>>>> 5c77924c26de2cac3ec99430555ac8fd545ca8d9
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

<<<<<<< HEAD
=======
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

>>>>>>> 5c77924c26de2cac3ec99430555ac8fd545ca8d9
        if (request.getSpecialization() != null) {
            doctor.setSpecialization(request.getSpecialization());
        }

        return doctorRepository.save(doctor);
    }
}