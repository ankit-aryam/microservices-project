package com.assignment.auth_service.service;

import com.assignment.auth_service.entity.UserOrder;
import com.assignment.auth_service.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserOrderConsumer {

    private final UserOrderRepository repository;

    @KafkaListener(topics = "order-events", groupId = "auth-service-group")
    public void consumeOrderCreatedEvent(Map<String, Object> event) {
        System.out.println("Received order event: " + event);

        if ("ORDER_CREATED".equals(event.get("event"))) {
            Map<String, Object> data = (Map<String, Object>) event.get("data");
            Long userId = Long.valueOf(data.get("userId").toString());
            Long orderId = Long.valueOf(data.get("orderId").toString());
            String productDetails = data.get("productDetails").toString();

            UserOrder userOrder = UserOrder.builder()
                    .userId(userId)
                    .orderId(orderId)
                    .productDetails(productDetails)
                    .orderTime(LocalDateTime.now())
                    .build();

            repository.save(userOrder);
            System.out.println("ORDER_CREATED consumed for userId: " + userId + ", orderId: " + orderId);
        }
    }
}
