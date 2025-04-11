package com.bithealth.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class PresciptionUpdateRequestDTO {
    private List<MedicineItem> medicineList;
    private LocalDate invoiceDate;
}
