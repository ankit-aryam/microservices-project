package com.assignment.order_service.service;

import com.assignment.order_service.entity.UserSession;
import com.assignment.order_service.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserSessionService {

    private final UserSessionRepository repository;

    @KafkaListener(
            topics = "user-events",
            groupId = "abcd",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeUserLoggedInEvent(Map<String, Object> event) {
        System.out.println("=== KAFKA MESSAGE RECEIVED ===");
        System.out.println("Received raw message: " + event);

        if("USER_LOGGED_IN".equals(event.get("event"))) {
            Map<String, Object> data = (Map<String, Object>) event.get("data");
            Long userId = Long.valueOf(data.get("userId").toString());

            UserSession session = UserSession.builder()
                    .userId(userId)
                    .lastLogin(LocalDateTime.now())
                    .build();

            repository.save(session);
            System.out.println("USER_LOGGED_IN consumed and saved for userId: " + userId);
        }
    }
}
