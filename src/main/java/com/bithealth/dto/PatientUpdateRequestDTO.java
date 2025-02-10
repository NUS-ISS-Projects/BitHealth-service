package com.bithealth.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientUpdateRequestDTO {
    private String dateOfBirth;
    private String gender;
    private String medicalHistory;
}