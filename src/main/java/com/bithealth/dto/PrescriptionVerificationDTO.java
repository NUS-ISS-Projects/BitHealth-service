package com.bithealth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrescriptionVerificationDTO {
    @JsonFormat(pattern = "M/d/yyyy, h:mm:ss a")
    private LocalDateTime lastVerified;
}