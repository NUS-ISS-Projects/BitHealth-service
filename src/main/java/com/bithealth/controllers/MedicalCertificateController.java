package com.bithealth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bithealth.entities.MedicalCertificate;
import com.bithealth.services.MedicalCertificateService;

@RestController
@RequestMapping("/api/medical-certificates")
public class MedicalCertificateController {
    @Autowired
    private MedicalCertificateService medicalCertificateService;

    @PostMapping
    public ResponseEntity<MedicalCertificate> createMedicalCertificate(@RequestBody MedicalCertificate certificate) {
        return ResponseEntity.status(201).body(medicalCertificateService.createMedicalCertificate(certificate));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<MedicalCertificate> getMedicalCertificateByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(medicalCertificateService.getMedicalCertificateByAppointment(appointmentId));
    }

    @PutMapping("/{certificateId}/verify")
    public ResponseEntity<MedicalCertificate> verifyMedicalCertificate(
            @PathVariable Long certificateId,
            @RequestParam Boolean isVerified) {
        return ResponseEntity.ok(medicalCertificateService.verifyMedicalCertificate(certificateId, isVerified));
    }
}
