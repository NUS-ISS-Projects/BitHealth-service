package com.bithealth.entities;

import jakarta.persistence.*;
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
    private String diagnosisAction;
}
