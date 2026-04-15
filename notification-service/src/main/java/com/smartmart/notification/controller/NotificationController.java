package com.smartmart.notification.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartmart.notification.entity.Notification;
import com.smartmart.notification.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	/**
	 * Send a new notification
	 * 
	 * @param notification the Notification to send
	 * @return ResponseEntity with sent notification
	 */
	@PostMapping("/send")
	public ResponseEntity<?> sendNotification(@RequestBody Notification notification) {
		try {
			Notification sentNotification = notificationService.sendNotification(notification);
			return ResponseEntity.status(HttpStatus.CREATED).body(sentNotification);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Error sending notification: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get notification by ID
	 * 
	 * @param id the Notification ID
	 * @return ResponseEntity with Notification if found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getNotificationById(@PathVariable Integer id) {
		try {
			Optional<Notification> notification = notificationService.getNotificationById(id);
			if (notification.isPresent()) {
				return ResponseEntity.ok(notification.get());
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Notification not found with id: " + id);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get notification by Order ID
	 * 
	 * @param orderId the Order ID
	 * @return ResponseEntity with Notification if found
	 */
	@GetMapping("/order/{orderId}")
	public ResponseEntity<?> getNotificationByOrderId(@PathVariable String orderId) {
		try {
			Optional<Notification> notification = notificationService.getNotificationByOrderId(orderId);
			if (notification.isPresent()) {
				return ResponseEntity.ok(notification.get());
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Notification not found for orderId: " + orderId);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get all notifications for a user
	 * 
	 * @param userId the User ID
	 * @return ResponseEntity with list of Notifications
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getNotificationsByUserId(@PathVariable String userId) {
		try {
			List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No notifications found for userId: " + userId);
			}
			return ResponseEntity.ok(notifications);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get notifications by status
	 * 
	 * @param status the notification status
	 * @return ResponseEntity with list of Notifications
	 */
	@GetMapping("/status/{status}")
	public ResponseEntity<?> getNotificationsByStatus(@PathVariable String status) {
		try {
			List<Notification> notifications = notificationService.getNotificationsByStatus(status);
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No notifications found with status: " + status);
			}
			return ResponseEntity.ok(notifications);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get notifications by channel
	 * 
	 * @param channel the notification channel
	 * @return ResponseEntity with list of Notifications
	 */
	@GetMapping("/channel/{channel}")
	public ResponseEntity<?> getNotificationsByChannel(@PathVariable String channel) {
		try {
			List<Notification> notifications = notificationService.getNotificationsByChannel(channel);
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No notifications found for channel: " + channel);
			}
			return ResponseEntity.ok(notifications);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get notifications by type
	 * 
	 * @param type the notification type
	 * @return ResponseEntity with list of Notifications
	 */
	@GetMapping("/type/{type}")
	public ResponseEntity<?> getNotificationsByType(@PathVariable String type) {
		try {
			List<Notification> notifications = notificationService.getNotificationsByType(type);
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No notifications found for type: " + type);
			}
			return ResponseEntity.ok(notifications);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get all notifications
	 * 
	 * @return ResponseEntity with list of all Notifications
	 */
	@GetMapping
	public ResponseEntity<?> getAllNotifications() {
		try {
			List<Notification> notifications = notificationService.getAllNotifications();
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No notifications found");
			}
			return ResponseEntity.ok(notifications);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get notifications by Order ID and User ID
	 * 
	 * @param orderId the Order ID
	 * @param userId the User ID
	 * @return ResponseEntity with list of Notifications
	 */
	@GetMapping("/order/{orderId}/user/{userId}")
	public ResponseEntity<?> getNotificationsByOrderIdAndUserId(@PathVariable String orderId,
			@PathVariable String userId) {
		try {
			List<Notification> notifications = notificationService
					.getNotificationsByOrderIdAndUserId(orderId, userId);
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No notifications found for orderId: " + orderId + " and userId: " + userId);
			}
			return ResponseEntity.ok(notifications);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Update an existing notification
	 * 
	 * @param id the Notification ID
	 * @param notification the updated Notification data
	 * @return ResponseEntity with updated Notification
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateNotification(@PathVariable Integer id, @RequestBody Notification notification) {
		try {
			Notification updatedNotification = notificationService.updateNotification(id, notification);
			return ResponseEntity.ok(updatedNotification);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Update notification status
	 * 
	 * @param id the Notification ID
	 * @param status the new status
	 * @return ResponseEntity with success message
	 */
	@PutMapping("/{id}/status")
	public ResponseEntity<?> updateNotificationStatus(@PathVariable Integer id, @RequestParam String status) {
		try {
			String message = notificationService.updateNotificationStatus(id, status);
			return ResponseEntity.ok(message);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Delete a notification
	 * 
	 * @param id the Notification ID
	 * @return ResponseEntity with success message
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotification(@PathVariable Integer id) {
		try {
			boolean deleted = notificationService.deleteNotification(id);
			if (deleted) {
				return ResponseEntity.ok("Notification deleted successfully");
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Notification not found with id: " + id);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Delete all notifications for an order
	 * 
	 * @param orderId the Order ID
	 * @return ResponseEntity with success message
	 */
	@DeleteMapping("/order/{orderId}")
	public ResponseEntity<?> deleteNotificationByOrderId(@PathVariable String orderId) {
		try {
			notificationService.deleteNotificationByOrderId(orderId);
			return ResponseEntity.ok("Notifications deleted successfully for orderId: " + orderId);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Validation Error: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get all failed notifications
	 * 
	 * @return ResponseEntity with list of failed Notifications
	 */
	@GetMapping("/failed")
	public ResponseEntity<?> getFailedNotifications() {
		try {
			List<Notification> notifications = notificationService.getFailedNotifications();
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No failed notifications found");
			}
			return ResponseEntity.ok(notifications);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Get all pending notifications
	 * 
	 * @return ResponseEntity with list of pending Notifications
	 */
	@GetMapping("/pending")
	public ResponseEntity<?> getPendingNotifications() {
		try {
			List<Notification> notifications = notificationService.getPendingNotifications();
			if (notifications.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("No pending notifications found");
			}
			return ResponseEntity.ok(notifications);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

	/**
	 * Retry sending failed notifications
	 * 
	 * @return ResponseEntity with success message
	 */
	@PostMapping("/retry-failed")
	public ResponseEntity<?> retryFailedNotifications() {
		try {
			notificationService.retryFailedNotifications();
			return ResponseEntity.ok("Failed notifications retry initiated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error: " + e.getMessage());
		}
	}

}
