package com.medicalapplication.medicalapplication.Services.auth;



import com.medicalapplication.medicalapplication.dto.SignUpRequest;
import com.medicalapplication.medicalapplication.dto.UserDto;
import com.medicalapplication.medicalapplication.entity.User;

import java.util.List;

public interface AuthService {
    UserDto createdCustomer(SignUpRequest signuprequest);
    boolean hascustomerwithemail(String email);

    List<User> getAllUsers();

    List<UserDto> getAllStudents();




}
