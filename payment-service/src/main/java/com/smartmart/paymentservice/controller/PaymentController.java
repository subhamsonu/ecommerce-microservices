package com.smartmart.paymentservice.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartmart.paymentservice.entity.Payment;
import com.smartmart.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private PaymentService paymentService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getPaymentById(@PathVariable Integer id) {
		try {
			log.debug("Fetching payment with id: {}", id);
			Optional<Payment> payment = paymentService.getPaymentById(id);
			if (payment.isPresent()) {
				log.debug("Payment found with id: {}", id);
				return ResponseEntity.ok(payment.get());
			}
			log.warn("Payment not found with id: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Payment not found with id: " + id);
		} catch (IllegalArgumentException e) {
			log.error("Validation error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error retrieving payment: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<?> getPaymentByOrderId(@PathVariable String orderId) {
		try {
			log.debug("Fetching payment for orderId: {}", orderId);
			Optional<Payment> payment = paymentService.getPaymentByOrderId(orderId);
			if (payment.isPresent()) {
				log.debug("Payment found for orderId: {}", orderId);
				return ResponseEntity.ok(payment.get());
			}
			log.warn("Payment not found for orderId: {}", orderId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Payment not found for orderId: " + orderId);
		} catch (IllegalArgumentException e) {
			log.error("Validation error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Error retrieving payment: {}", e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

}

