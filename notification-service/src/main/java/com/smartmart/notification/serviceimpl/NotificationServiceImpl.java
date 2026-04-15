package com.smartmart.notification.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartmart.notification.entity.Notification;
import com.smartmart.notification.repository.NotificationRepository;
import com.smartmart.notification.service.NotificationService;
import com.smartmart.notification.validator.NotificationValidator;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private NotificationValidator notificationValidator;

	/**
	 * Send a new notification
	 * 
	 * @param notification the Notification to send
	 * @return the saved Notification
	 */
	@Override
	public Notification sendNotification(Notification notification) {
		notificationValidator.validateNotification(notification);

		notification.setCreatedAt(LocalDateTime.now());
		notification.setStatus("PENDING");
		notification.setRetryCount(0);

		Notification savedNotification = notificationRepository.save(notification);

		// TODO: Integrate with actual email/SMS/push notification service
		// For now, marking as SENT
		savedNotification.setStatus("SENT");
		savedNotification.setSentAt(LocalDateTime.now());

		return notificationRepository.save(savedNotification);
	}

	/**
	 * Get notification by ID
	 * 
	 * @param id the Notification ID
	 * @return Optional containing Notification if found
	 */
	@Override
	public Optional<Notification> getNotificationById(Integer id) {
		notificationValidator.validateId(id);
		return notificationRepository.findById(id);
	}

	/**
	 * Get notification by Order ID
	 * 
	 * @param orderId the Order ID
	 * @return Optional containing Notification if found
	 */
	@Override
	public Optional<Notification> getNotificationByOrderId(String orderId) {
		notificationValidator.validateOrderId(orderId);
		return notificationRepository.findByOrderId(orderId);
	}

	/**
	 * Get all notifications for a user
	 * 
	 * @param userId the User ID
	 * @return List of Notifications
	 */
	@Override
	public List<Notification> getNotificationsByUserId(String userId) {
		notificationValidator.validateUserId(userId);
		return notificationRepository.findByUserId(userId);
	}

	/**
	 * Get notifications by status
	 * 
	 * @param status the notification status
	 * @return List of Notifications
	 */
	@Override
	public List<Notification> getNotificationsByStatus(String status) {
		notificationValidator.validateStatus(status);
		return notificationRepository.findByStatus(status);
	}

	/**
	 * Get notifications by channel
	 * 
	 * @param channel the notification channel (EMAIL, SMS, PUSH_NOTIFICATION, IN_APP)
	 * @return List of Notifications
	 */
	@Override
	public List<Notification> getNotificationsByChannel(String channel) {
		notificationValidator.validateChannel(channel);
		return notificationRepository.findByChannel(channel);
	}

	/**
	 * Get notifications by type
	 * 
	 * @param notificationType the notification type
	 * @return List of Notifications
	 */
	@Override
	public List<Notification> getNotificationsByType(String notificationType) {
		notificationValidator.validateNotificationType(notificationType);
		return notificationRepository.findByNotificationType(notificationType);
	}

	/**
	 * Get all notifications
	 * 
	 * @return List of all Notifications
	 */
	@Override
	public List<Notification> getAllNotifications() {
		return notificationRepository.findAll();
	}

	/**
	 * Get notifications by Order ID and User ID
	 * 
	 * @param orderId the Order ID
	 * @param userId the User ID
	 * @return List of Notifications
	 */
	@Override
	public List<Notification> getNotificationsByOrderIdAndUserId(String orderId, String userId) {
		notificationValidator.validateOrderId(orderId);
		notificationValidator.validateUserId(userId);
		return notificationRepository.findByOrderIdAndUserId(orderId, userId);
	}

	/**
	 * Update an existing notification
	 * 
	 * @param id the Notification ID
	 * @param notification the updated Notification data
	 * @return the updated Notification
	 */
	@Override
	public Notification updateNotification(Integer id, Notification notification) {
		notificationValidator.validateId(id);
		notificationValidator.validateNotification(notification);

		Optional<Notification> existingNotification = notificationRepository.findById(id);
		if (existingNotification.isPresent()) {
			Notification notif = existingNotification.get();
			if (notification.getMessage() != null) {
				notif.setMessage(notification.getMessage());
			}
			if (notification.getStatus() != null) {
				notif.setStatus(notification.getStatus());
			}
			if (notification.getRecipient() != null) {
				notif.setRecipient(notification.getRecipient());
			}
			notif.setUpdatedAt(LocalDateTime.now());
			return notificationRepository.save(notif);
		}
		throw new RuntimeException("Notification not found with id: " + id);
	}

	/**
	 * Update notification status
	 * 
	 * @param id the Notification ID
	 * @param status the new status
	 * @return success message
	 */
	@Override
	public String updateNotificationStatus(Integer id, String status) {
		notificationValidator.validateId(id);
		notificationValidator.validateStatus(status);

		Optional<Notification> notification = notificationRepository.findById(id);
		if (notification.isPresent()) {
			Notification notif = notification.get();
			notif.setStatus(status);
			if ("DELIVERED".equals(status) || "READ".equals(status)) {
				notif.setSentAt(LocalDateTime.now());
			}
			notif.setUpdatedAt(LocalDateTime.now());
			notificationRepository.save(notif);
			return "Notification status updated successfully";
		}
		throw new RuntimeException("Notification not found with id: " + id);
	}

	/**
	 * Delete a notification
	 * 
	 * @param id the Notification ID
	 * @return true if deleted successfully
	 */
	@Override
	public boolean deleteNotification(Integer id) {
		notificationValidator.validateId(id);
		if (notificationRepository.existsById(id)) {
			notificationRepository.deleteById(id);
			return true;
		}
		return false;
	}

	/**
	 * Delete all notifications for an order
	 * 
	 * @param orderId the Order ID
	 */
	@Override
	public void deleteNotificationByOrderId(String orderId) {
		notificationValidator.validateOrderId(orderId);
		notificationRepository.deleteByOrderId(orderId);
	}

	/**
	 * Get all failed notifications
	 * 
	 * @return List of failed Notifications
	 */
	@Override
	public List<Notification> getFailedNotifications() {
		return notificationRepository.findByStatus("FAILED");
	}

	/**
	 * Get all pending notifications
	 * 
	 * @return List of pending Notifications
	 */
	@Override
	public List<Notification> getPendingNotifications() {
		return notificationRepository.findByStatus("PENDING");
	}

	/**
	 * Retry sending failed notifications
	 */
	@Override
	public void retryFailedNotifications() {
		List<Notification> failedNotifications = getFailedNotifications();

		for (Notification notification : failedNotifications) {
			if (notification.getRetryCount() < 3) { // Max 3 retries
				notification.setRetryCount(notification.getRetryCount() + 1);
				notification.setStatus("PENDING");
				notificationRepository.save(notification);
				// TODO: Integrate with actual notification service to retry
			}
		}
	}

}
