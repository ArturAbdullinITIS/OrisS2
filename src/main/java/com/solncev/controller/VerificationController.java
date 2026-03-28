package com.solncev.controller;

import com.solncev.model.User;
import com.solncev.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    private final UserRepository userRepository;

    public VerificationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/verification")
    @Transactional
    public ResponseEntity<String> verify(@RequestParam("code") String code) {
        User user = userRepository.findByVerificationCode(code).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid verification code");
        }

        if (Boolean.TRUE.equals(user.getVerified())) {
            return ResponseEntity.ok("Already verified");
        }

        user.setVerified(true);
        user.setVerificationCode(null);
        userRepository.save(user);

        return ResponseEntity.ok("Verified");
    }
}