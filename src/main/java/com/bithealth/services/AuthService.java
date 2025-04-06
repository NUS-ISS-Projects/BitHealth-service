package com.bithealth.services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bithealth.dto.UserRegistrationDTO;
import com.bithealth.entities.Doctor;
import com.bithealth.entities.Patient;
import com.bithealth.entities.User;
import com.bithealth.entities.User.Role;
import com.bithealth.repositories.DoctorRepository;
import com.bithealth.repositories.PatientRepository;
import com.bithealth.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AuthService(UserRepository userRepository, DoctorRepository doctorRepository,
            PatientRepository patientRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    public User registerUser(UserRegistrationDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        // Save the user first
        User savedUser = userRepository.save(user);

        // If the user is a doctor, create a corresponding Doctor entry
        if (Role.DOCTOR.equals(savedUser.getRole())) {
            Doctor doctor = new Doctor();
            doctor.setUser(savedUser);
            doctor.setSpecialization("General Practitioner"); // Default specialization

            // Save the doctor entry
            doctorRepository.save(doctor);
        }

        // If the user is a patient, create a corresponding Patient entry
        if (Role.PATIENT.equals(savedUser.getRole())) {
            Patient patient = new Patient();
            patient.setUser(savedUser);
            patient.setDateOfBirth("1990-01-01"); // Default date of birth
            patient.setGender("Unknown"); // Default gender

            // Save the patient entry
            patientRepository.save(patient);
        }

        return savedUser;
    }

    public User getUserProfile(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + userEmail);
        }
        return userOptional.get();
    }


}
