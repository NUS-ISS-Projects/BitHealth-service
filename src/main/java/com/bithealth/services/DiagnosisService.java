package com.bithealth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithealth.dto.DiagnosisUpdateDTO;
import com.bithealth.entities.Appointment;
import com.bithealth.entities.Diagnosis;
import com.bithealth.repositories.AppointmentRepository;
import com.bithealth.repositories.DiagnosisRepository;

@Service
public class DiagnosisService {
    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Diagnosis addOrUpdateDiagnosis(Long appointmentId, DiagnosisUpdateDTO dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + appointmentId));

        Diagnosis diagnosis = diagnosisRepository.findByAppointment_AppointmentId(appointmentId)
                .orElseGet(Diagnosis::new);


        diagnosis.setAppointment(appointment);
        diagnosis.setDiagnosis(dto.getDiagnosis());


        return diagnosisRepository.save(diagnosis);
    }
    public Diagnosis getDiagnosisByAppointment(Long appointmentId) {
        return diagnosisRepository.findByAppointment_AppointmentId(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Diagnosis not found for appointment with ID: " + appointmentId));
    }
}
