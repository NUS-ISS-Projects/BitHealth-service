package com.bithealth.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalCertificateUpdateRequestDTO {
    private Integer noOfDays;
    private LocalDate effectFrom;
    private LocalDate issueDate;
}
