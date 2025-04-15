package com.bithealth.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalCertificateCreateRequestDTO {
    private Long appointmentId;        // to link the certificate to an appointment
    private String certificateNumber;  // "MC No."
    private Integer noOfDays;
    private LocalDate effectFrom;
    private LocalDate issueDate;       // if you want them to input the actual issuance date
}