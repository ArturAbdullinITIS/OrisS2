package com.solncev.controller;

import com.solncev.dto.RegisterRequest;
import com.solncev.model.User;
import com.solncev.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User created = registrationService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Registered user id=" + created.getId());
    }
}