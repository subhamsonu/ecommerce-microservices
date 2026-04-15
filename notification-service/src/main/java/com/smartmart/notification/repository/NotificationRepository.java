package com.smartmart.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartmart.notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

	Optional<Notification> findByOrderId(String orderId);

	List<Notification> findByUserId(String userId);

	List<Notification> findByStatus(String status);

	List<Notification> findByChannel(String channel);

	List<Notification> findByNotificationType(String notificationType);

	List<Notification> findByOrderIdAndUserId(String orderId, String userId);

	List<Notification> findByRecipient(String recipient);

	List<Notification> findByStatusAndChannel(String status, String channel);

	void deleteByOrderId(String orderId);

}
