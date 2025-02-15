package com.bithealth.dto;

import lombok.Data;

@Data
public class PatientUpdateRequestDTO {
    private String dateOfBirth;
    private String gender;
    private String medicalHistory;
}