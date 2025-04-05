package com.bithealth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bithealth.dto.MedicalCertificateCreateRequestDTO;
import com.bithealth.dto.MedicalCertificateVerificationDTO;
import com.bithealth.entities.MedicalCertificate;
import com.bithealth.services.MedicalCertificateService;

@RestController
@RequestMapping("/api/medical-certificates")
public class MedicalCertificateController {
    @Autowired
    private MedicalCertificateService medicalCertificateService;

    @PostMapping
    public ResponseEntity<MedicalCertificate> createMedicalCertificate(
            @RequestBody MedicalCertificateCreateRequestDTO dto) {
        MedicalCertificate certificate = medicalCertificateService.createMedicalCertificate(dto);
        return ResponseEntity.status(201).body(certificate);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<MedicalCertificate> getMedicalCertificateByAppointment(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(medicalCertificateService.getMedicalCertificateByAppointment(appointmentId));
    }

    @PutMapping("/verify/{certificateId}")
    public ResponseEntity<MedicalCertificate> verifyMedicalCertificate(
            @PathVariable Long certificateId,
            @RequestBody MedicalCertificateVerificationDTO dto) {
        MedicalCertificate updatedCertificate = medicalCertificateService.verifyMedicalCertificate(certificateId, dto);
        return ResponseEntity.ok(updatedCertificate);
    }
}
