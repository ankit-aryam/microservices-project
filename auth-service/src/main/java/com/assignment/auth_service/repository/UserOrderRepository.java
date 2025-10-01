package com.assignment.auth_service.repository;

import com.assignment.auth_service.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Long> {

}
