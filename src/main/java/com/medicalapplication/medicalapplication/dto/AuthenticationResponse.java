package com.medicalapplication.medicalapplication.dto;



import com.medicalapplication.medicalapplication.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private Long userId;
}
