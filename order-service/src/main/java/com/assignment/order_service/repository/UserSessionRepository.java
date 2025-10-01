package com.assignment.order_service.repository;

import com.assignment.order_service.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

}
