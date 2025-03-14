package com.bithealth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRescheduleDTO {
    @JsonProperty("appointment_date")
    private LocalDate appointmentDate;
    @JsonProperty("appointment_time")
    private LocalTime appointmentTime;
}
