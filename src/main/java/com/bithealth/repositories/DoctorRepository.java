package com.bithealth.repositories;

import com.bithealth.entities.Doctor;
import com.bithealth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);

    Optional<Doctor> findById(Long doctorId);
}