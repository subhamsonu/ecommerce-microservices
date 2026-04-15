package com.smartmart.inventory_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.smartmart.inventory_service.dto.OrderEventDto;
import com.smartmart.inventory_service.dto.PaymentDto;
import com.smartmart.inventory_service.dto.PaymentStatus;
import com.smartmart.inventory_service.service.InventoryService;

@Service
public class KafkaConsumer {

	private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

	@Autowired
	private InventoryService inventoryService;

	@KafkaListener(topics = "order-topic", groupId = "inventory-service-group", containerFactory = "kafkaListenerContainerFactory")

	public void orderConsumer(OrderEventDto order) {
		log.info("Consumed order: {}", order.getOrderId());

		inventoryService.reserveInventory(order.getProductId(), order.getQuantity());

	}

	@KafkaListener(topics = "payment-topic", groupId = "inventory-payment-group", containerFactory = "paymentListenerContainerFactory")
	public void consume(PaymentDto paymentDto) {

		try {
			PaymentStatus status = PaymentStatus.fromString(paymentDto.getStatus());
			if (status == PaymentStatus.SUCCESS) {
				log.info("Processing successful payment for order: {}", paymentDto.getOrderId());
				inventoryService.deductStock(paymentDto);
				log.info("Successfully deducted stock for order: {}", paymentDto.getOrderId());
			} else if (status == PaymentStatus.FAILED) {
				log.warn("Processing failed payment for order: {}", paymentDto.getOrderId());
				inventoryService.releaseStock(paymentDto);
				log.info("Successfully released stock for order: {}", paymentDto.getOrderId());
			}
		} catch (IllegalStateException e) {
			log.error("State error processing payment for order {}: {}", paymentDto.getOrderId(), e.getMessage());
		} catch (Exception e) {
			log.error("Error processing payment for order {}: ", paymentDto.getOrderId(), e);
		}

	}
}
