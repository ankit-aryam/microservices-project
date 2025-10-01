package com.assignment.order_service.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private Double amount;
    private String productName;
}
