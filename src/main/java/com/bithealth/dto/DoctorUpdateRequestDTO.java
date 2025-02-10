package com.bithealth.dto;

import lombok.Data;

@Data
public class DoctorUpdateRequestDTO {
    private String specialization;
    private String bio;
}