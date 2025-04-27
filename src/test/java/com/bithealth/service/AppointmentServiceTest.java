package com.bithealth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bithealth.dto.AppointmentCreateRequestDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Doctor;
import com.bithealth.entities.Patient;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.DoctorRepository;
import com.bithealth.repositories.PatientRepository;
import com.bithealth.services.AppointmentService;

public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAppointment() {
        // Arrange
        AppointmentCreateRequestDTO request = new AppointmentCreateRequestDTO();
        request.setPatientId(1L);
        request.setDoctorId(2L);
        request.setAppointmentDate(LocalDate.now());
        request.setAppointmentTime(LocalTime.now());
        request.setReasonForVisit("Checkup");
        request.setComment("Follow-up");

        Patient patient = new Patient();
        patient.setPatientId(1L);
        Doctor doctor = new Doctor();
        doctor.setDoctorId(2L);
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReasonForVisit(request.getReasonForVisit());
        appointment.setComment(request.getComment());
        appointment.setStatus(Appointment.Status.PENDING);


        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Act
        Appointment createdAppointment = appointmentService.createAppointment(request);

        // Assert
        assertNotNull(createdAppointment);
        assertEquals(1L, createdAppointment.getAppointmentId());
        assertEquals(patient, createdAppointment.getPatient());
        assertEquals(doctor, createdAppointment.getDoctor());
        assertEquals(request.getAppointmentDate(), createdAppointment.getAppointmentDate());
        assertEquals(request.getAppointmentTime(), createdAppointment.getAppointmentTime());
        assertEquals(request.getReasonForVisit(), createdAppointment.getReasonForVisit());
        assertEquals(request.getComment(), createdAppointment.getComment());
        assertEquals(Appointment.Status.PENDING, createdAppointment.getStatus());
        verify(patientRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).findById(2L);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    public void testCreateAppointmentPatientNotFound() {
        // Arrange
        AppointmentCreateRequestDTO request = new AppointmentCreateRequestDTO();
        request.setPatientId(1L);
        request.setDoctorId(2L);
        request.setAppointmentDate(LocalDate.now());
        request.setAppointmentTime(LocalTime.now());
        request.setReasonForVisit("Checkup");
        request.setComment("Follow-up");

        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.createAppointment(request);
        });
        assertEquals("Patient not found with ID: 1", exception.getMessage());
        verify(patientRepository, times(1)).findById(1L);
        verify(doctorRepository, never()).findById(2L);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    public void testCreateAppointmentDoctorNotFound() {
        // Arrange
         AppointmentCreateRequestDTO request = new AppointmentCreateRequestDTO();
        request.setPatientId(1L);
        request.setDoctorId(2L);
        request.setAppointmentDate(LocalDate.now());
        request.setAppointmentTime(LocalTime.now());
        request.setReasonForVisit("Checkup");
        request.setComment("Follow-up");

        Patient patient = new Patient();
        patient.setPatientId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.createAppointment(request);
        });
        assertEquals("Doctor not found with ID: 2", exception.getMessage());
        verify(patientRepository, times(1)).findById(1L);
        verify(doctorRepository, times(1)).findById(2L);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }



    // @Test
    // public void testGetAppointmentById() {
    //     // Arrange
    //     Long appointmentId = 1L;
    //     Appointment appointment = new Appointment();
    //     appointment.setAppointmentId(appointmentId);

    //     when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment)); // Changed to findById

    //     // Act
    //     Appointment result = appointmentService.getAppointmentById(appointmentId);

    //     // Assert
    //     assertEquals(appointment, result);
    //     verify(appointmentRepository, times(1)).findById(appointmentId); // Changed to findById
    // }

    // @Test
    // public void testGetAppointmentByIdNotFound() {
    //     // Arrange
    //     Long appointmentId = 1L;
    //     when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

    //     // Act
    //     Appointment result = appointmentService.getAppointmentById(appointmentId);

    //     // Assert
    //     assertNull(result);
    //     verify(appointmentRepository, times(1)).findById(appointmentId);
    // }

    @Test
    public void testGetAppointmentsByDoctorId() {
        // Arrange
        Long doctorId = 1L;
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1L);
        appointment1.setDoctor(new Doctor());
        appointment1.getDoctor().setDoctorId(doctorId);
        appointments.add(appointment1);

        when(appointmentRepository.findByDoctor_DoctorId(doctorId)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAvailableAppointmentsForDoctor(doctorId);

        // Assert
        assertEquals(appointments, result);
        verify(appointmentRepository, times(1)).findByDoctor_DoctorId(doctorId);
    }

    @Test
    public void testGetAppointmentsByPatientId() {
        // Arrange
        Long patientId = 1L;
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment1 = new Appointment();
        appointment1.setAppointmentId(1L);
        appointment1.setPatient(new Patient());
        appointment1.getPatient().setPatientId(patientId);
        appointments.add(appointment1);

        when(appointmentRepository.findByPatient_PatientId(patientId)).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAppointmentsForPatient(patientId);

        // Assert
        assertEquals(appointments, result);
        verify(appointmentRepository, times(1)).findByPatient_PatientId(patientId);
    }

    @Test
    public void testUpdateAppointmentStatus() {
        // Arrange
        Long appointmentId = 1L;
        String status = "CANCELLED";
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        appointment.setStatus(Appointment.Status.PENDING);

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        // Act
        Appointment updatedAppointment = appointmentService.updateAppointmentStatus(appointmentId, status);

        // Assert
        assertEquals(Appointment.Status.CANCELLED, updatedAppointment.getStatus());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    public void testUpdateAppointmentStatusNotFound() {
        // Arrange
        Long appointmentId = 1L;
        String status = "CANCELLED";
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.updateAppointmentStatus(appointmentId, status);
        });
        assertEquals("Appointment not found with ID: 1", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    public void testUpdateAppointmentDateTime() {
        // Arrange
        Long appointmentId = 1L;
        LocalDate newDate = LocalDate.now().plusDays(1);
        LocalTime newTime = LocalTime.now().plusHours(1);
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(appointmentId);
        appointment.setAppointmentDate(LocalDate.now());
        appointment.setAppointmentTime(LocalTime.now());

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        // Act
        Appointment updatedAppointment = appointmentService.updateAppointmentDateTime(appointmentId, newDate, newTime);

        // Assert
        assertEquals(newDate, updatedAppointment.getAppointmentDate());
        assertEquals(newTime, updatedAppointment.getAppointmentTime());
        assertEquals(Appointment.Status.PENDING, updatedAppointment.getStatus());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).save(appointment);
    }

     @Test
    public void testUpdateAppointmentDateTimeNotFound() {
        // Arrange
        Long appointmentId = 1L;
        LocalDate newDate = LocalDate.now().plusDays(1);
        LocalTime newTime = LocalTime.now().plusHours(1);
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.updateAppointmentDateTime(appointmentId, newDate, newTime);
        });
        assertEquals("Appointment not found with ID: 1", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}