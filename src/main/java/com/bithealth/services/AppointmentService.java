package com.bithealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.entities.Appointment;
import com.bithealth.repositories.AppointmentRepository;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAvailableAppointmentsForDoctor(Long doctorId) {
        return appointmentRepository.findByDoctor_DoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsForPatient(Long patientId) {
        return appointmentRepository.findByPatient_PatientId(patientId);
    }

    public Appointment updateAppointmentStatus(Long appointmentId, Appointment.Status status) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
}