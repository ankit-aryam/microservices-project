package com.assignment.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Double amount;
    private String productName;
}
