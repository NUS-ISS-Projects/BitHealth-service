package com.bithealth.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String email;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedDate // Automatically sets the creation timestamp
    private LocalDateTime createdAt;

    @LastModifiedDate // Automatically updates the timestamp on every update
    private LocalDateTime updatedAt;

    // One-to-One relationship with Doctor
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JsonIgnore
    private Doctor doctor;

    // One-to-One relationship with Patient
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JsonIgnore
    private Patient patient;
}