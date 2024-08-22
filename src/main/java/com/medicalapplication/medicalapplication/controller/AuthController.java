package com.medicalapplication.medicalapplication.controller;


import com.medicalapplication.medicalapplication.Services.JWT.UserService;
import com.medicalapplication.medicalapplication.Services.auth.AuthServiceImpl;
import com.medicalapplication.medicalapplication.dto.AuthenticationRequest;
import com.medicalapplication.medicalapplication.dto.AuthenticationResponse;
import com.medicalapplication.medicalapplication.dto.SignUpRequest;
import com.medicalapplication.medicalapplication.dto.UserDto;
import com.medicalapplication.medicalapplication.entity.User;
import com.medicalapplication.medicalapplication.repostory.UserRepostory;
import com.medicalapplication.medicalapplication.utill.JWTUtill;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTUtill jwtUtill;
    private final UserRepostory userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignUpRequest signupRequest) {
        if (authService.hascustomerwithemail(signupRequest.getEmail()))
            return new ResponseEntity<>("email already exists", HttpStatus.NOT_ACCEPTABLE);
        UserDto createduserdto = authService.createdCustomer(signupRequest);
        if (createduserdto == null) return new ResponseEntity<>(
                "customer not created", HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(createduserdto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public AuthenticationResponse createauthenticationtoken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException, DisabledException, UsernameNotFoundException, BadRequestException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadRequestException("incorrect username or passoword");
        }
        final UserDetails userDetails = userService.userDetailService().loadUserByUsername(authenticationRequest.getEmail());
        System.out.print(userDetails.getUsername());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        final String jwt = jwtUtill.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }
        return authenticationResponse;

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = authService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}


