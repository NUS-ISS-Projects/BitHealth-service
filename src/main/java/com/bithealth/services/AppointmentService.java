package com.bithealth.services;

import com.bithealth.dto.AppointmentCreateRequestDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Doctor;
import com.bithealth.entities.Patient;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.DoctorRepository;
import com.bithealth.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Appointment createAppointment(AppointmentCreateRequestDTO request) {
        // Fetch the Patient and Doctor objects by their IDs
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Patient not found with ID: " + request.getPatientId()));

        // Fetch the Doctor by their ID
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + request.getDoctorId()));

        // Create the Appointment entity
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);

        // Parse date and time strings into LocalDate and LocalTime
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReasonForVisit(request.getReasonForVisit());
        appointment.setComment(request.getComment());
        appointment.setStatus(Appointment.Status.PENDING); // Default status

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAvailableAppointmentsForDoctor(Long doctorId) {
        return appointmentRepository.findByDoctor_DoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatient_PatientId(patientId);
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findAppointmentByAppointmentId(appointmentId);
    }
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
        appointmentRepository.delete(appointment);
    }

    // Update the status of an appointment
    public Appointment updateAppointmentStatus(Long appointmentId, String status) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));
        // Set status
        appointment.setStatus(Appointment.Status.valueOf(status.toUpperCase()));
        return appointmentRepository.save(appointment);
    }
}