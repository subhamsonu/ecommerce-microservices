package com.smartmart.notification.validator;

import org.springframework.stereotype.Component;

@Component
public class NotificationValidator {

	private static final String[] VALID_STATUSES = { "PENDING", "SENT", "FAILED", "DELIVERED", "READ" };
	private static final String[] VALID_CHANNELS = { "EMAIL", "SMS", "PUSH_NOTIFICATION", "IN_APP" };
	private static final String[] VALID_NOTIFICATION_TYPES = { "ORDER_CREATED", "PAYMENT_SUCCESS", "PAYMENT_FAILED",
			"ORDER_SHIPPED", "ORDER_DELIVERED", "ORDER_CANCELLED", "INVENTORY_LOW", "PROMOTION", "ACCOUNT_UPDATE" };

	/**
	 * Validate notification object
	 * 
	 * @param notification the Notification to validate
	 * @throws IllegalArgumentException if validation fails
	 */
	public void validateNotification(Object notification) {
		if (notification == null) {
			throw new IllegalArgumentException("Notification cannot be null");
		}
	}

	/**
	 * Validate Notification ID
	 * 
	 * @param id the Notification ID
	 * @throws IllegalArgumentException if ID is invalid
	 */
	public void validateId(Integer id) {
		if (id == null || id <= 0) {
			throw new IllegalArgumentException("Notification ID must be a positive number");
		}
	}

	/**
	 * Validate Order ID
	 * 
	 * @param orderId the Order ID
	 * @throws IllegalArgumentException if Order ID is invalid
	 */
	public void validateOrderId(String orderId) {
		if (orderId == null || orderId.trim().isEmpty()) {
			throw new IllegalArgumentException("Order ID cannot be null or empty");
		}
	}

	/**
	 * Validate User ID
	 * 
	 * @param userId the User ID
	 * @throws IllegalArgumentException if User ID is invalid
	 */
	public void validateUserId(String userId) {
		if (userId == null || userId.trim().isEmpty()) {
			throw new IllegalArgumentException("User ID cannot be null or empty");
		}
	}

	/**
	 * Validate notification status
	 * 
	 * @param status the notification status
	 * @throws IllegalArgumentException if status is invalid
	 */
	public void validateStatus(String status) {
		if (status == null || status.trim().isEmpty()) {
			throw new IllegalArgumentException("Status cannot be null or empty");
		}

		boolean isValid = false;
		for (String validStatus : VALID_STATUSES) {
			if (validStatus.equals(status)) {
				isValid = true;
				break;
			}
		}

		if (!isValid) {
			throw new IllegalArgumentException("Invalid status: " + status + ". Valid statuses are: PENDING, SENT, FAILED, DELIVERED, READ");
		}
	}

	/**
	 * Validate notification channel
	 * 
	 * @param channel the notification channel
	 * @throws IllegalArgumentException if channel is invalid
	 */
	public void validateChannel(String channel) {
		if (channel == null || channel.trim().isEmpty()) {
			throw new IllegalArgumentException("Channel cannot be null or empty");
		}

		boolean isValid = false;
		for (String validChannel : VALID_CHANNELS) {
			if (validChannel.equals(channel)) {
				isValid = true;
				break;
			}
		}

		if (!isValid) {
			throw new IllegalArgumentException("Invalid channel: " + channel + ". Valid channels are: EMAIL, SMS, PUSH_NOTIFICATION, IN_APP");
		}
	}

	/**
	 * Validate notification type
	 * 
	 * @param notificationType the notification type
	 * @throws IllegalArgumentException if notification type is invalid
	 */
	public void validateNotificationType(String notificationType) {
		if (notificationType == null || notificationType.trim().isEmpty()) {
			throw new IllegalArgumentException("Notification type cannot be null or empty");
		}

		boolean isValid = false;
		for (String validType : VALID_NOTIFICATION_TYPES) {
			if (validType.equals(notificationType)) {
				isValid = true;
				break;
			}
		}

		if (!isValid) {
			throw new IllegalArgumentException("Invalid notification type: " + notificationType);
		}
	}

	/**
	 * Validate email address
	 * 
	 * @param email the email address
	 * @throws IllegalArgumentException if email is invalid
	 */
	public void validateEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}

		String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
		if (!email.matches(emailPattern)) {
			throw new IllegalArgumentException("Invalid email format: " + email);
		}
	}

	/**
	 * Validate phone number
	 * 
	 * @param phoneNumber the phone number
	 * @throws IllegalArgumentException if phone number is invalid
	 */
	public void validatePhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
			throw new IllegalArgumentException("Phone number cannot be null or empty");
		}

		if (!phoneNumber.matches("\\d{10}")) {
			throw new IllegalArgumentException("Invalid phone number format. Expected 10 digits: " + phoneNumber);
		}
	}

	/**
	 * Validate recipient based on channel
	 * 
	 * @param recipient the recipient (email, phone, or user ID)
	 * @param channel the notification channel
	 * @throws IllegalArgumentException if recipient is invalid for the channel
	 */
	public void validateRecipient(String recipient, String channel) {
		if (recipient == null || recipient.trim().isEmpty()) {
			throw new IllegalArgumentException("Recipient cannot be null or empty");
		}

		switch (channel) {
		case "EMAIL":
			validateEmail(recipient);
			break;
		case "SMS":
			validatePhoneNumber(recipient);
			break;
		case "PUSH_NOTIFICATION":
		case "IN_APP":
			// For push and in-app, recipient should be user ID
			if (recipient.trim().isEmpty()) {
				throw new IllegalArgumentException("Recipient (User ID) cannot be empty for " + channel + " channel");
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown channel: " + channel);
		}
	}

}
