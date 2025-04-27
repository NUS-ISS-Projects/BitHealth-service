package com.bithealth.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "diagnosis")
@Data
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosisId;

    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "appointmentId")
    private Appointment appointment;

    private String diagnosis;
    private String prescription; // Renamed from diagnosisAction

    public Diagnosis() {
        // Default constructor for JPA
    }

    public Diagnosis(Long diagnosisId, Long appointmentId, String diagnosis, String prescription) {
        this.diagnosisId = diagnosisId;
        this.appointment = new Appointment(appointmentId, appointmentId, null, null);
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }
}