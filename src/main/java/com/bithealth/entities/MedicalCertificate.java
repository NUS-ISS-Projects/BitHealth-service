package com.bithealth.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "medical_certificates")
@EntityListeners(AuditingEntityListener.class)
public class MedicalCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointmentId")
    private Appointment appointment;

    private LocalDate issueDate;
    private String details;

    private Boolean isVerified;

    @PrePersist
    protected void onCreate() {
        issueDate = LocalDate.now(); // Automatically set today's date on creation
    }
}