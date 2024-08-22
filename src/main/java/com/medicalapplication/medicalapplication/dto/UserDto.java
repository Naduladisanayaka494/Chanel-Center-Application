package com.medicalapplication.medicalapplication.dto;


import com.medicalapplication.medicalapplication.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {



    private Long id;
    private String name;
    private String email;

    private UserRole userRole;

}
