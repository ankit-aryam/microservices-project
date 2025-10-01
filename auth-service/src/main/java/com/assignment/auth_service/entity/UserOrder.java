package com.assignment.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long orderId;

    @Column(length = 1000)
    private String productDetails;

    private LocalDateTime orderTime;
}
