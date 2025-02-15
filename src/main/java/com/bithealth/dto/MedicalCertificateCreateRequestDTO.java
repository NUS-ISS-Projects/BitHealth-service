package com.bithealth.dto;

import lombok.Data;

@Data
public class MedicalCertificateCreateRequestDTO {
    private Long appointmentId;
    private String details;
}