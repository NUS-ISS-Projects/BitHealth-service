package com.bithealth.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bithealth.controllers.DoctorController;
import com.bithealth.dto.DoctorUpdateRequestDTO;
import com.bithealth.entities.Doctor;
import com.bithealth.entities.User;
import com.bithealth.services.DoctorService;

public class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorService doctorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void testGetAllDoctors() {
    //     // Arrange
    //     List<Doctor> doctors = new ArrayList<>();
    //     Doctor doctor1 = new Doctor();
    //     doctor1.setDoctorId(1L);
    //     doctor1.getUser().setName("Doctor Smith");
    //     Doctor doctor2 = new Doctor();
    //     doctor2.setDoctorId(2L);
    //     doctor2.getUser().setName("Doctor Jones");
    //     doctors.add(doctor1);
    //     doctors.add(doctor2);

    //     when(doctorService.getAllDoctors()).thenReturn(doctors);

    //     // Act
    //     ResponseEntity<List<Doctor>> responseEntity = doctorController.getAllDoctors();

    //     // Assert
    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    //     assertEquals(doctors, responseEntity.getBody());
    //     verify(doctorService, times(1)).getAllDoctors();
    // }

    // @Test
    // public void testGetDoctorProfile_Found() {
    //     // Arrange
    //     Long doctorId = 1L;
    //     Doctor doctor = new Doctor();
    //     doctor.setDoctorId(doctorId);
    //     doctor.getUser().setName("Doctor Smith");

    //     when(doctorService.getDoctorProfile(doctorId)).thenReturn(Optional.of(doctor));

    //     // Act
    //     ResponseEntity<Doctor> responseEntity = doctorController.getDoctorProfile(doctorId);

    //     // Assert
    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    //     assertEquals(doctor, responseEntity.getBody());
    //     verify(doctorService, times(1)).getDoctorProfile(doctorId);
    // }

    @Test
    public void testGetDoctorProfile_NotFound() {
        // Arrange
        Long doctorId = 1L;
        when(doctorService.getDoctorProfile(doctorId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Doctor> responseEntity = doctorController.getDoctorProfile(doctorId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(doctorService, times(1)).getDoctorProfile(doctorId);
    }

    // @Test
    // public void testGetDoctorProfileUserId_Found() {
    //     //Arrange
    //     Long userId = 1L;
    //     Doctor doctor = new Doctor();
    //     doctor.setDoctorId(1L);
    //     doctor.getUser().setUserId(userId);
    //     doctor.getUser().setName("Doctor Smith");

    //     when(doctorService.getUserDoctorProfile(userId)).thenReturn(Optional.of(doctor));

    //     //Act
    //     ResponseEntity<Doctor> responseEntity = doctorController.getDoctorProfileUserId(userId);

    //     //Assert
    //     assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    //     assertEquals(doctor, responseEntity.getBody());
    //     verify(doctorService, times(1)).getUserDoctorProfile(userId);
    // }

    @Test
    public void testGetDoctorProfileUserId_NotFound() {
        //Arrange
        Long userId = 1L;
        when(doctorService.getUserDoctorProfile(userId)).thenReturn(Optional.empty());

        //Act
        ResponseEntity<Doctor> responseEntity = doctorController.getDoctorProfileUserId(userId);

        //Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(doctorService, times(1)).getUserDoctorProfile(userId);
    }

    @Test
    public void testUpdateDoctorProfile() {
        // Arrange
        Long doctorId = 1L;
        DoctorUpdateRequestDTO requestDTO = new DoctorUpdateRequestDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setEmail("updated@example.com");
        requestDTO.setSpecialization("Cardiology");
        requestDTO.setAvatar("new_avatar_url");

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setDoctorId(doctorId);
        User updatedUser = new User();
        updatedUser.setName("Updated Name");
        updatedUser.setEmail("updated@example.com");
        updatedDoctor.setUser(updatedUser);
        updatedDoctor.setSpecialization("Cardiology");
        updatedDoctor.setAvatar("new_avatar_url");

        when(doctorService.updateDoctorProfile(doctorId, requestDTO)).thenReturn(updatedDoctor);

        // Act
        ResponseEntity<Doctor> responseEntity = doctorController.updateDoctorProfile(doctorId, requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedDoctor, responseEntity.getBody());
        verify(doctorService, times(1)).updateDoctorProfile(doctorId, requestDTO);
    }
}