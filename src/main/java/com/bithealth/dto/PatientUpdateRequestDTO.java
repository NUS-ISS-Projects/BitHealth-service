package com.bithealth.dto;

import lombok.Data;

@Data
public class PatientUpdateRequestDTO {
    private String avatar;
    private String contact_number;
    private String dateOfBirth;
    private String gender;

    private String name;
    private String email;
}