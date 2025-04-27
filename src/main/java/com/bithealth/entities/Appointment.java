package com.bithealth.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

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
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctorId")
    @JsonIgnore
    private Doctor doctor;

    @NotNull
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    @Size(max = 255)
    private String reasonForVisit;

    @Size(max = 500)
    @ToString.Exclude
    private String comment;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    public enum Status {
        PENDING, CONFIRMED, CANCELLED, REJECTED, COMPLETED
    }

    public Appointment() {
        // Default constructor for JPA
    }

    public Appointment(Long patientId, Long doctorId, LocalDate appointmentDate, LocalTime appointmentTime) {
        this.patient = new Patient(patientId, null, comment, comment, comment, comment);
        this.doctor = new Doctor(doctorId, null, comment, comment);
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }



}