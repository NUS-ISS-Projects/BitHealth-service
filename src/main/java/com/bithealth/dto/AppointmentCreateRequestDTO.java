package com.bithealth.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AppointmentCreateRequestDTO {
    private Long patientId;
    private Long doctorId;
    @JsonProperty("appointment_date") // Matches JSON key "appointment_date"
    private LocalDate appointmentDate;

    @JsonProperty("appointment_time") // Matches JSON key "appointment_time"
    private LocalTime appointmentTime;

    @JsonProperty("reason_for_visit") // Matches JSON key "reason_for_visit"
    private String reasonForVisit;

    @JsonProperty("comment") // Matches JSON key "comment"
    private String comment;
}