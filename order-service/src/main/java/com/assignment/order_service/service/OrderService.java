package com.assignment.order_service.service;

import com.assignment.order_service.dto.OrderRequest;
import com.assignment.order_service.dto.OrderResponse;
import com.assignment.order_service.entity.Order;
import com.assignment.order_service.entity.UserSession;
import com.assignment.order_service.repository.OrderRepository;
import com.assignment.order_service.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserSessionRepository userSessionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderResponse createOrder(OrderRequest request) {

        Optional<UserSession> session = userSessionRepository.findById(request.getUserId());
        if (session.isEmpty()) {
            throw new RuntimeException("User not logged in! Cannot place order.");
        }


        Order order = Order.builder()
                .userId(request.getUserId())
                .amount(request.getAmount())
                .productName(request.getProductName())
                .build();

        order = orderRepository.save(order);

        // Publish event
        Map<String, Object> event = new HashMap<>();
        event.put("event", "ORDER_CREATED");

        Map<String, Object> data = new HashMap<>();
        data.put("orderId", order.getOrderId());
        data.put("userId", order.getUserId());
        data.put("amount", order.getAmount());
        data.put("productDetails", order.getProductName());

        event.put("data", data);

        kafkaTemplate.send("order-events", event);

        return new OrderResponse(order.getOrderId(), order.getUserId(), order.getAmount(), order.getProductName());
    }
}
