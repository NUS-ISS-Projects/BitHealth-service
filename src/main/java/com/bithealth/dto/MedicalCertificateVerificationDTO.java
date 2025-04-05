package com.bithealth.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MedicalCertificateVerificationDTO {
    private LocalDateTime lastVerified;
}