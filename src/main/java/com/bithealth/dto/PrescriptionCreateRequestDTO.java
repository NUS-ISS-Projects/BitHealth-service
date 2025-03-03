package com.bithealth.dto;

import lombok.Data;

@Data
public class PrescriptionCreateRequestDTO {
    private Long appointmentId;
    private String medicines; // Accepts a list of medicines
    private String dosage;
    private String duration;
    private String notes;
}