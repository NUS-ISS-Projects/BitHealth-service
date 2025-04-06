package com.bithealth.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PresciptionUpdateRequestDTO {
    private List<MedicineItem> medicineList;
    private LocalDate invoiceDate;
}
