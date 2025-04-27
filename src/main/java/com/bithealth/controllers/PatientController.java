package com.bithealth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithealth.dto.PatientUpdateRequestDTO;
import com.bithealth.entities.Patient;
import com.bithealth.services.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // Get Patient Profile
    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientProfile(@PathVariable Long patientId) {
        return patientService.getPatientProfile(patientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Update Patient Profile
    @PutMapping("/{patientId}")
    public ResponseEntity<Patient> updatePatientProfile(
            @PathVariable Long patientId,
            @RequestBody PatientUpdateRequestDTO request) {
        return ResponseEntity.ok(patientService.updatePatientProfile(patientId, request));
    }


    // Get profile using JWT token 
    // Get Patient Profile
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Patient> getPatientProfileWithUser(@PathVariable Long userId) {
        return patientService.getPatientProfileUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}