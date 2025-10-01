package com.assignment.auth_service.service;

import com.assignment.auth_service.dto.RegisterRequest;
import com.assignment.auth_service.dto.RegisterResponse;
import com.assignment.auth_service.entity.User;
import com.assignment.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest request) {
        // Check if email exists
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return new RegisterResponse(user.getId(), user.getName(), user.getEmail());
    }
}
