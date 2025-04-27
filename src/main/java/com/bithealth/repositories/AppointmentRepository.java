package com.bithealth.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bithealth.entities.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor_DoctorId(Long doctorId);

    List<Appointment> findByPatient_PatientId(Long patientId);

    Appointment findAppointmentByAppointmentId(Long appointmentId);
}