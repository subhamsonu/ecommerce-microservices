package com.smartmart.paymentservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smartmart.paymentservice.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	Optional<Payment> findByOrderId(String orderId);

	List<Payment> findByStatus(String status);

	List<Payment> findByOrderIdAndStatus(String orderId, String status);

	@Transactional
	void deleteByOrderId(String orderId);

	@Modifying
	@Transactional
	@Query("UPDATE Payment p SET p.status = :status WHERE p.orderId = :orderId")
	void updateStatusByOrderId(@Param("orderId") String orderId, @Param("status") String status);

	@Modifying
	@Transactional
	@Query("UPDATE Payment p SET p.status = :status, p.createdAt = CURRENT_TIMESTAMP WHERE p.id = :id")
	void updatePaymentStatus(@Param("id") Integer id, @Param("status") String status);

}
