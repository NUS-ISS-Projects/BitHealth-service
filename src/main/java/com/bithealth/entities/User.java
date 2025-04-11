package com.bithealth.entities;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
<<<<<<< HEAD
=======
import jakarta.persistence.*;
import lombok.*;
>>>>>>> 5c77924c26de2cac3ec99430555ac8fd545ca8d9

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
<<<<<<< HEAD
=======
import java.time.LocalDateTime;
>>>>>>> 5c77924c26de2cac3ec99430555ac8fd545ca8d9

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    public enum Role {
        PATIENT, DOCTOR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;
<<<<<<< HEAD
    private String passwordHash;
=======

    private String firebaseUid;
>>>>>>> 5c77924c26de2cac3ec99430555ac8fd545ca8d9

    @Enumerated(EnumType.STRING)
    private Role role;

    // One-to-One relationship with Doctor
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JsonIgnore
    private Doctor doctor;

    // One-to-One relationship with Patient
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JsonIgnore
    private Patient patient;
<<<<<<< HEAD

    // Getters and Setters
    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
=======
>>>>>>> 5c77924c26de2cac3ec99430555ac8fd545ca8d9
}