package com.bithealth.repositories;
import com.bithealth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bithealth.entities.User;

public interface  UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);

}
