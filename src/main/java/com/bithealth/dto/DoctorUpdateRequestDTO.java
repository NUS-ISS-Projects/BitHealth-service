package com.bithealth.dto;

import lombok.Data;

@Data
public class DoctorUpdateRequestDTO {
    private String avatar;
    private String name;
    private String email;
    private String specialization;
}