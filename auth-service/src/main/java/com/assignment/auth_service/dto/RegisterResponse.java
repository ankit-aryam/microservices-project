package com.assignment.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String name;
    private String email;
}
