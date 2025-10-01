package com.assignment.auth_service.service;

import com.assignment.auth_service.dto.LoginRequest;
import com.assignment.auth_service.dto.LoginResponse;
import com.assignment.auth_service.dto.RegisterRequest;
import com.assignment.auth_service.dto.RegisterResponse;
import com.assignment.auth_service.entity.User;
import com.assignment.auth_service.repository.UserRepository;
import com.assignment.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .createdAt(java.time.LocalDateTime.now())
                .build();
        userRepository.save(user);
        return new RegisterResponse(user.getId(), user.getName(), user.getEmail());
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());

        // Publish Kafka event
        Map<String, Object> event = new HashMap<>();
        event.put("event", "USER_LOGGED_IN");
        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        data.put("email", user.getEmail());
        event.put("data", data);

        kafkaTemplate.send("user-events", user.getId().toString(), event);

        return new LoginResponse(token, user.getId(), user.getEmail());
    }
}
