package com.bithealth.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PrescriptionCreateRequestDTO {
    private Long appointmentId;
    private List<MedicineItem> medicineList;
    private String invoiceNo;
    private LocalDate invoiceDate;
}