package com.bithealth.entities;

import com.bithealth.helpers.MedicineItemListConverter;
import com.bithealth.dto.MedicineItem;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "prescriptions")
@EntityListeners(AuditingEntityListener.class)

public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointmentId")
    private Appointment appointment;

    @Convert(converter = MedicineItemListConverter.class)
    @Column(name = "medicine_list", columnDefinition = "jsonb")
    private List<MedicineItem> medicineList;

    private String invoiceNo;
    private LocalDate invoiceDate;

    private Boolean isVerified;
}