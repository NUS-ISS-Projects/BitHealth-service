package com.bithealth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithealth.dto.MedicalCertificateCreateRequestDTO;
import com.bithealth.dto.MedicalCertificateUpdateRequestDTO;
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

<<<<<<< HEAD
    @PutMapping("/{certificateId}/verify")
=======
    @PutMapping("/{certificateId}")
    public ResponseEntity<MedicalCertificate> updateMedicalCertificate(
            @PathVariable Long certificateId,
            @RequestBody MedicalCertificateUpdateRequestDTO dto) {
        MedicalCertificate updatedCertificate = medicalCertificateService.updateMedicalCertificate(certificateId, dto);
        return ResponseEntity.ok(updatedCertificate);
    }

    @PutMapping("/verify/{certificateId}")
>>>>>>> 5c77924c26de2cac3ec99430555ac8fd545ca8d9
    public ResponseEntity<MedicalCertificate> verifyMedicalCertificate(
            @PathVariable Long certificateId,
            @RequestBody MedicalCertificateVerificationDTO dto) {
        MedicalCertificate updatedCertificate = medicalCertificateService.verifyMedicalCertificate(certificateId, dto);
        return ResponseEntity.ok(updatedCertificate);
    }
}
