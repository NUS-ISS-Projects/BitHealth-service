package com.bithealth.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bithealth.entities.Doctor;
import com.bithealth.entities.User;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(Optional<User> user);

    Optional<Doctor> findById(Long doctorId);

    @Query("SELECT d FROM Doctor d JOIN FETCH d.user u")
    List<Doctor> findAllWithUsers();
}