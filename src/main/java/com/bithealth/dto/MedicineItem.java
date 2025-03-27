package com.bithealth.dto;

import lombok.Data;

@Data
public class MedicineItem {
    private String medicineName;
    private String purpose;
    private String dosage;
    private String duration;
    private String notes;
}
