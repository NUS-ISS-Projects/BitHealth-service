package com.bithealth.dto;

import lombok.Data;

@Data
public class AppointmentResponseDTO {
    private Long appointmentId;
    private String patientName; // Include only necessary patient details
    private String doctorName; // Include only necessary doctor details
    private String appointmentDate;
    private String appointmentTime;
    private String reasonForVisit;
    private String status;
    private String createdAt;
    private String updatedAt;
}