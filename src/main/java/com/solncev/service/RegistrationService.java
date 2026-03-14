package com.solncev.service;

import com.solncev.model.Role;
import com.solncev.model.User;
import com.solncev.repository.RoleRepository;
import com.solncev.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    private static final String ROLE_USER = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String rawPassword) {
        userRepository.findByUsername(username).ifPresent(u -> {
            throw new IllegalArgumentException("Username already exists");
        });

        Role userRole = roleRepository.findByName(ROLE_USER)
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName(ROLE_USER);
                    return roleRepository.save(r);
                });

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(List.of(userRole));

        return userRepository.save(user);
    }
}