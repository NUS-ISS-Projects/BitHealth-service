package com.bithealth.dto;

import lombok.Data;

@Data
public class DiagnosisUpdateDTO {
    private String diagnosis;
    private String prescription; // Updated field name to match JSON payload
}