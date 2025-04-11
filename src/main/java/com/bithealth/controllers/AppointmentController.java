package com.bithealth.controllers;

import com.bithealth.dto.AppointmentCreateRequestDTO;
import com.bithealth.dto.AppointmentStatusUpdateDTO;
import com.bithealth.dto.AppointmentRescheduleDTO;
import com.bithealth.dto.DiagnosisUpdateDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Diagnosis;
import com.bithealth.entities.User;
import com.bithealth.services.AppointmentService;
import com.bithealth.services.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DiagnosisService diagnosisService;

    // Create an Appointment
    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Appointment> createAppointment( @RequestHeader("Authorization") String authorizationHeader,
                                                          @RequestBody AppointmentCreateRequestDTO request) {
        Appointment appointment = appointmentService.createAppointment(request);
        return ResponseEntity.status(201).body(appointment);
    }

    // Get Available Appointments for a Doctor
    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<Appointment>> getAvailableAppointmentsForDoctor() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long doctorId = authUser.getDoctor().getDoctorId();
        return ResponseEntity.ok(appointmentService.getAvailableAppointmentsForDoctor(doctorId));
    }

    // Get Appointments for a Patient
    @GetMapping("/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<Appointment>> getAppointmentsForPatient() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long patientId = authUser.getPatient().getPatientId();
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(patientId));
    }

    // Get Appointment by ID
    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    // Update Appointment Date and Time (Reschedule)
    @PutMapping("/reschedule/{appointmentId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<Appointment> updateAppointmentDateTime(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentRescheduleDTO dto) {
        Appointment updatedAppointment = appointmentService.updateAppointmentDateTime(appointmentId,
                dto.getAppointmentDate(), dto.getAppointmentTime());
        return ResponseEntity.ok(updatedAppointment);
    }

    // Update Appointment Status
    @PutMapping("/updateStatus/{appointmentId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentStatusUpdateDTO dto) {
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, dto.getStatus());
        return ResponseEntity.ok(updatedAppointment);
    }

    @PutMapping("/diagnosis/{appointmentId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Diagnosis> updateDiagnosis(
            @PathVariable Long appointmentId,
            @RequestBody DiagnosisUpdateDTO dto) {
        Diagnosis diagnosis = diagnosisService.addOrUpdateDiagnosis(appointmentId, dto);
        return ResponseEntity.ok(diagnosis);
    }
    @GetMapping("/diagnosis/{appointmentId}")
    public ResponseEntity<Diagnosis> getDiagnosisByAppointment(@PathVariable Long appointmentId) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisByAppointment(appointmentId);
        return ResponseEntity.ok(diagnosis);
    }
}