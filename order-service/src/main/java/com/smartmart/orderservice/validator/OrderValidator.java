package com.smartmart.orderservice.validator;

import org.springframework.stereotype.Component;

import com.smartmart.orderservice.entity.Order;

@Component
public class OrderValidator {

	public void validateOrder(Order orderEvent) {
		if (orderEvent == null) {
			throw new IllegalArgumentException("OrderEvent cannot be null");
		}

		// Validate orderId
		if (orderEvent.getId() == null || orderEvent.getId() <= 0) {
			throw new IllegalArgumentException("OrderId cannot be null or empty");
		}

		// Validate productId
		if (orderEvent.getProductId() == null || orderEvent.getProductId() <= 0) {
			throw new IllegalArgumentException("ProductId cannot be null or empty");
		}

		// Validate quantity
		if (orderEvent.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than 0");
		}

		// Validate amount
		if (orderEvent.getAmount() <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0");
		}

	}

	/**
	 * Validates a new order (without ID validation since it's auto-generated)
	 * 
	 * @param orderEvent the order to validate
	 * @throws IllegalArgumentException if validation fails
	 */
	public void validateNewOrder(Order orderEvent) {
		if (orderEvent == null) {
			throw new IllegalArgumentException("Order cannot be null");
		}

		// Validate productId (ID should not be set for new orders)
		if (orderEvent.getProductId() == null || orderEvent.getProductId() <= 0) {
			throw new IllegalArgumentException("ProductId cannot be null or empty");
		}

		// Validate quantity
		if (orderEvent.getQuantity() <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than 0");
		}

		// Validate amount
		if (orderEvent.getAmount() <= 0) {
			throw new IllegalArgumentException("Amount must be greater than 0");
		}
	}

	/**
	 * Validates the OrderEvent ID
	 * 
	 * @param id the ID to validate
	 * @throws IllegalArgumentException if ID is invalid
	 */
	public void validateOrderId(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("OrderEvent ID must be a positive integer");
		}
	}

	/**
	 * Validates the OrderEvent status
	 * 
	 * @param status the status to validate
	 * @throws IllegalArgumentException if status is invalid
	 */
	public void validateOrderStatus(String status) {
		if (status == null || status.trim().isEmpty()) {
			throw new IllegalArgumentException("Status cannot be null or empty");
		}

		// Define valid statuses
		String[] validStatuses = { "PENDING", "CONFIRMED", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED" };

		boolean isValid = false;
		for (String validStatus : validStatuses) {
			if (validStatus.equals(status.toUpperCase())) {
				isValid = true;
				break;
			}
		}

		if (!isValid) {
			throw new IllegalArgumentException(
					"Invalid status: " + status + ". Valid statuses are: PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED");
		}
	}

	/**
	 * Validates OrderId format
	 * 
	 * @param orderId the orderId to validate
	 * @throws IllegalArgumentException if orderId format is invalid
	 */
	public void validateOrderIdFormat(String orderId) {
		if (orderId == null || orderId.trim().isEmpty()) {
			throw new IllegalArgumentException("OrderId cannot be null or empty");
		}

		if (!orderId.matches("^ORD-\\d+$")) {
			throw new IllegalArgumentException(
					"Invalid OrderId format. Expected format: ORD-[numbers], but got: " + orderId);
		}
	}

	/**
	 * Validates ProductId format
	 * 
	 * @param productId the productId to validate
	 * @throws IllegalArgumentException if productId format is invalid
	 */
	public void validateProductIdFormat(String productId) {
		if (productId == null || productId.trim().isEmpty()) {
			throw new IllegalArgumentException("ProductId cannot be null or empty");
		}

		if (!productId.matches("^PROD-\\d+$")) {
			throw new IllegalArgumentException(
					"Invalid ProductId format. Expected format: PROD-[numbers], but got: " + productId);
		}
	}

}
