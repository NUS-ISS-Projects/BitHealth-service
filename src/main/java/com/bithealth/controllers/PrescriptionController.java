package com.bithealth.controllers;

import java.io.IOException;

import com.bithealth.dto.PresciptionUpdateRequestDTO;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bithealth.dto.PrescriptionCreateRequestDTO;
import com.bithealth.dto.PrescriptionVerificationDTO;
import com.bithealth.entities.Prescription;
import com.bithealth.services.PrescriptionService;
import com.itextpdf.text.DocumentException;

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

    @GetMapping("/{prescriptionId}/download")
    public ResponseEntity<Resource> downloadPrescription(
            @PathVariable Long prescriptionId,
            @RequestParam String format) throws IOException, DocumentException {
        // Generate the file
        Resource file = prescriptionService.generatePrescriptionFile(prescriptionId, format);

        // Set headers
        String contentType = "application/pdf".equals(format) ? "application/pdf"
                : "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=prescription." + format)
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }
}