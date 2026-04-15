package com.smartmart.paymentservice.validator;

import org.springframework.stereotype.Component;

import com.smartmart.paymentservice.entity.Payment;

@Component
public class PaymentValidator {

	public void validatePayment(Payment payment) {
		if (payment == null) {
			throw new IllegalArgumentException("Payment cannot be null");
		}

		// Validate orderId
		if (payment.getOrderId() == null || payment.getOrderId().trim().isEmpty()) {
			throw new IllegalArgumentException("OrderId cannot be null or empty");
		}

		// Validate amount
		if (payment.getAmount() == null || payment.getAmount() <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0");
		}

	}

	public void validatePaymentId(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Payment ID must be a positive integer");
		}
	}

	public void validateOrderId(String orderId) {
		if (orderId == null || orderId.trim().isEmpty()) {
			throw new IllegalArgumentException("OrderId cannot be null or empty");
		}
	}

}
