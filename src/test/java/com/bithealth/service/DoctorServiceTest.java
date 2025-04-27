package com.bithealth.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// DoctorService Test
import com.bithealth.dto.DoctorUpdateRequestDTO;
import com.bithealth.entities.Doctor;
import com.bithealth.entities.User;
import com.bithealth.repositories.DoctorRepository;
import com.bithealth.repositories.UserRepository;
import com.bithealth.services.DoctorService;

public class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllDoctors() {
        // Arrange
        List<Doctor> doctors = new ArrayList<>();
        Doctor doctor1 = new Doctor();
        doctor1.setDoctorId(1L);
        doctor1.setUser(new User());
        Doctor doctor2 = new Doctor();
        doctor2.setDoctorId(2L);
        doctor2.setUser(new User());
        doctors.add(doctor1);
        doctors.add(doctor2);

        when(doctorRepository.findAllWithUsers()).thenReturn(doctors);

        // Act
        List<Doctor> result = doctorService.getAllDoctors();

        // Assert
        assertEquals(doctors, result);
        verify(doctorRepository, times(1)).findAllWithUsers();
    }

    @Test
    public void testGetDoctorProfile_Found() {
        // Arrange
        Long doctorId = 1L;
        Doctor doctor = new Doctor();
        doctor.setDoctorId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        // Act
        Optional<Doctor> result = doctorService.getDoctorProfile(doctorId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(doctor, result.get());
        verify(doctorRepository, times(1)).findById(doctorId);
    }

    @Test
    public void testGetDoctorProfile_NotFound() {
        // Arrange
        Long doctorId = 1L;
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act
        Optional<Doctor> result = doctorService.getDoctorProfile(doctorId);

        // Assert
        assertFalse(result.isPresent());
        verify(doctorRepository, times(1)).findById(doctorId);
    }

    @Test
    public void testGetUserDoctorProfile_Found() {
        //Arrange
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1L);
        doctor.setUser(user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(doctorRepository.findByUser(Optional.of(user))).thenReturn(Optional.of(doctor));

        //Act
        Optional<Doctor> result = doctorService.getUserDoctorProfile(userId);

        //Assert
        assertTrue(result.isPresent());
        assertEquals(doctor, result.get());
        verify(userRepository, times(1)).findById(userId);
        verify(doctorRepository, times(1)).findByUser(Optional.of(user));
    }

    @Test
    public void testGetUserDoctorProfile_NotFound() {
        //Arrange
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(doctorRepository.findByUser(Optional.of(user))).thenReturn(Optional.empty());

        //Act
        Optional<Doctor> result = doctorService.getUserDoctorProfile(userId);

        //Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
        verify(doctorRepository, times(1)).findByUser(Optional.of(user));
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

        Doctor existingDoctor = new Doctor();
        existingDoctor.setDoctorId(doctorId);
        User existingUser = new User();
        existingUser.setName("Original Name");
        existingUser.setEmail("original@example.com");
        existingDoctor.setUser(existingUser);
        existingDoctor.setSpecialization("General Practitioner");
        existingDoctor.setAvatar("original_avatar_url");

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(existingDoctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(existingDoctor);

        // Act
        Doctor updatedDoctor = doctorService.updateDoctorProfile(doctorId, requestDTO);

        // Assert
        assertEquals(doctorId, updatedDoctor.getDoctorId());
        assertEquals("Updated Name", updatedDoctor.getUser().getName());
        assertEquals("updated@example.com", updatedDoctor.getUser().getEmail());
        assertEquals("Cardiology", updatedDoctor.getSpecialization());
        assertEquals("new_avatar_url", updatedDoctor.getAvatar());
        verify(doctorRepository, times(1)).findById(doctorId);
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    public void testUpdateDoctorProfile_DoctorNotFound() {
        // Arrange
        Long doctorId = 1L;
        DoctorUpdateRequestDTO requestDTO = new DoctorUpdateRequestDTO();
        requestDTO.setName("Updated Name");

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            doctorService.updateDoctorProfile(doctorId, requestDTO);
        });

        assertEquals("Doctor not found with ID: " + doctorId, exception.getMessage());
        verify(doctorRepository, times(1)).findById(doctorId);
        verify(doctorRepository, never()).save(any(Doctor.class));
    }
}
