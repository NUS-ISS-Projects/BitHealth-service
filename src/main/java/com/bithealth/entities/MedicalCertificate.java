package com.bithealth.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "medical_certificates")
@EntityListeners(AuditingEntityListener.class)
public class MedicalCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long certificateId;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointmentId")
    private Appointment appointment;

    private String certificateNumber;

    private LocalDate issueDate;
    private Integer noOfDays;
    private LocalDate effectFrom;
    private LocalDateTime lastVerified;
    private Boolean isVerified;
}