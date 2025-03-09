package com.bithealth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bithealth.entities.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor_DoctorId(Long doctorId);

    List<Appointment> findByPatient_PatientId(Long patientId);

    Appointment findAppointmentByAppointmentId(Long appointmentId);
}