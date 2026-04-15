package com.smartmart.orderservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.smartmart.orderservice.dto.OrderEventDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderProducer {
	
	private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);
	private static final String TOPIC = "order-topic";
	
	private final KafkaTemplate<String, OrderEventDto> kafkaTemplate;
	
	public void sendOrder(OrderEventDto orderEvent) {
		try {
			log.info("Sending order event to Kafka topic '{}': orderId={}", TOPIC, orderEvent.getOrderId());
			kafkaTemplate.send(TOPIC, orderEvent);
			log.debug("Order event sent successfully for orderId: {}", orderEvent.getOrderId());
		} catch (Exception e) {
			log.error("Error sending order event for orderId: {}: {}", orderEvent.getOrderId(), e.getMessage(), e);
			throw new RuntimeException("Failed to send order event: " + e.getMessage(), e);
		}
	}
	
}
