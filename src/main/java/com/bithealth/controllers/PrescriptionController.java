package com.bithealth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bithealth.entities.Prescription;
import com.bithealth.services.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        return ResponseEntity.status(201).body(prescriptionService.createPrescription(prescription));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Prescription> getPrescriptionByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionByAppointment(appointmentId));
    }

    @PutMapping("/{prescriptionId}/verify")
    public ResponseEntity<Prescription> verifyPrescription(
            @PathVariable Long prescriptionId,
            @RequestParam Boolean isVerified) {
        return ResponseEntity.ok(prescriptionService.verifyPrescription(prescriptionId, isVerified));
    }
}