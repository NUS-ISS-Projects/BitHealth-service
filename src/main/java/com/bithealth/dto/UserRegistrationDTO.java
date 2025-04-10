package com.bithealth.dto;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String role;
    private String firebaseUid;
}
