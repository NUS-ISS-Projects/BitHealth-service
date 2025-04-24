package com.bithealth.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithealth.dto.DoctorUpdateRequestDTO;
import com.bithealth.entities.Doctor;
import com.bithealth.services.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * Get List of Doctors
     * - GET /api/doctors
     */
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    /**
     * Get a Doctor’s Profile
     * - GET /api/doctors/{doctorId}
     */
    @GetMapping("/{doctorId}")
    public ResponseEntity<Doctor> getDoctorProfile(@PathVariable Long doctorId) {
        return doctorService.getDoctorProfile(doctorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Doctor> getDoctorProfileUserId(@PathVariable Long userId) {
        return doctorService.getUserDoctorProfile(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Update Doctor’s Profile
     * - PUT /api/doctors/{doctorId}
     */
    @PutMapping("/{doctorId}")
    public ResponseEntity<Doctor> updateDoctorProfile(
            @PathVariable Long doctorId,
            @RequestBody DoctorUpdateRequestDTO request) {
        return ResponseEntity.ok(doctorService.updateDoctorProfile(doctorId, request));
    }
}