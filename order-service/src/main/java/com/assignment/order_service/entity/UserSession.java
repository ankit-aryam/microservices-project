package com.assignment.order_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {

    @Id
    private Long userId;

    private LocalDateTime lastLogin;
}
