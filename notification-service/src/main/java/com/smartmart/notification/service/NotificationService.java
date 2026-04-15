package com.smartmart.notification.service;

import java.util.List;
import java.util.Optional;

import com.smartmart.notification.entity.Notification;

public interface NotificationService {

	Notification sendNotification(Notification notification);

	Optional<Notification> getNotificationById(Integer id);

	Optional<Notification> getNotificationByOrderId(String orderId);

	List<Notification> getNotificationsByUserId(String userId);

	List<Notification> getNotificationsByStatus(String status);

	List<Notification> getNotificationsByChannel(String channel);

	List<Notification> getNotificationsByType(String notificationType);

	List<Notification> getAllNotifications();

	List<Notification> getNotificationsByOrderIdAndUserId(String orderId, String userId);

	Notification updateNotification(Integer id, Notification notification);

	String updateNotificationStatus(Integer id, String status);

	boolean deleteNotification(Integer id);

	void deleteNotificationByOrderId(String orderId);

	List<Notification> getFailedNotifications();

	List<Notification> getPendingNotifications();

	void retryFailedNotifications();

}
