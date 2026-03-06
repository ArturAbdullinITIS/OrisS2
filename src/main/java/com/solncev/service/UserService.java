package com.solncev.service;

import com.solncev.dto.UserDto;
import com.solncev.model.User;
import com.solncev.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createUser(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        userRepository.save(user);
    }
}