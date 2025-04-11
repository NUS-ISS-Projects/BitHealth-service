package com.bithealth.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MedicalCertificateVerificationDTO {
    @JsonFormat(pattern = "M/d/yyyy, h:mm:ss a")
    private LocalDateTime lastVerified;
}