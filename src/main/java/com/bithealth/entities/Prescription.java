package com.bithealth.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.bithealth.dto.MedicineItem;
import com.bithealth.helpers.MedicineItemListConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

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
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "medicine_list", columnDefinition = "jsonb")
    private List<MedicineItem> medicineList;

    private String invoiceNo;
    private LocalDate invoiceDate;
    private LocalDateTime lastVerified;
    private Boolean isVerified;
}