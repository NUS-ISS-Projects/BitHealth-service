package com.bithealth.controllers;

import com.bithealth.dto.PresciptionUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bithealth.dto.PrescriptionCreateRequestDTO;
import com.bithealth.dto.PrescriptionVerificationDTO;
import com.bithealth.entities.Prescription;
import com.bithealth.services.PrescriptionService;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody PrescriptionCreateRequestDTO dto) {
        Prescription prescription = prescriptionService.createPrescription(dto);
        return ResponseEntity.status(201).body(prescription);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Prescription> getPrescriptionByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(prescriptionService.getPrescriptionByAppointment(appointmentId));
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long prescriptionId,@RequestBody PresciptionUpdateRequestDTO dto) {
        Prescription prescription = prescriptionService.updatePrescription(prescriptionId, dto);
        return ResponseEntity.ok(prescription);
    }

    @PutMapping("/verify/{prescriptionId}")
    public ResponseEntity<Prescription> verifyPrescription(
            @PathVariable Long prescriptionId,
            @RequestBody PrescriptionVerificationDTO dto) {
        Prescription updatedPrescription = prescriptionService.verifyPrescription(prescriptionId, dto);
        return ResponseEntity.ok(updatedPrescription);
    }
}