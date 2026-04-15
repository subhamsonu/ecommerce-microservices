package com.smartmart.orderservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartmart.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	@Query(value = "SELECT * FROM orders WHERE order_id = :orderId", nativeQuery = true)
	Optional<Order> findByOrderId(@Param("orderId") String orderId);
	
}