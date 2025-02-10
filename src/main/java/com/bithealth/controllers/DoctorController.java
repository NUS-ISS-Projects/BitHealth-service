package com.bithealth.controllers;

import com.bithealth.entities.Doctor;
import com.bithealth.services.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bithealth.dto.DoctorUpdateRequestDTO;
import java.util.List;

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