package com.bithealth.controllers;

import com.bithealth.dto.AppointmentCreateRequestDTO;
import com.bithealth.dto.AppointmentStatusUpdateDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Create an Appointment
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentCreateRequestDTO request) {
        Appointment appointment = appointmentService.createAppointment(request);
        return ResponseEntity.status(201).body(appointment);
    }

    // Get Available Appointments for a Doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAvailableAppointmentsForDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getAvailableAppointmentsForDoctor(doctorId));
    }

    // Get Appointments for a Patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(patientId));
    }

    // Update Appointment Status
    @PutMapping("/{appointmentId}")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentStatusUpdateDTO dto) {
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, dto.getStatus());
        return ResponseEntity.ok(updatedAppointment);
    }
}