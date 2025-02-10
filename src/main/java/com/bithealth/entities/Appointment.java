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
@Table(name = "appointments")
@EntityListeners(AuditingEntityListener.class)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctorId")
    private Doctor doctor;

    private LocalDate appointmentDate;
    private LocalDateTime appointmentTime;
    private String reasonForVisit;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate // Automatically sets the creation timestamp
    private LocalDateTime createdAt;

    @LastModifiedDate // Automatically updates the timestamp on every update
    private LocalDateTime updatedAt;

    public enum Status {
        PENDING, CONFIRMED, REJECTED, COMPLETED
    }
}