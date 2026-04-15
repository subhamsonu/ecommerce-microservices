package com.smartmart.notification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String orderId;

	private String userId;

	private String subject;

	private String message;

	private String notificationType; // ORDER_CREATED, PAYMENT_SUCCESS, PAYMENT_FAILED, ORDER_SHIPPED, ORDER_DELIVERED, etc.

	private String channel; // EMAIL, SMS, PUSH_NOTIFICATION, IN_APP

	private String status; // PENDING, SENT, FAILED, DELIVERED, READ

	private LocalDateTime createdAt;

	private LocalDateTime sentAt;

	private LocalDateTime updatedAt;

	private String recipient; // email, phone, or user ID based on channel

	private Integer retryCount; // Number of retries

	private String failureReason; // Reason for failure if any

	private String templateName; // Name of email/SMS template used

}
